import warnings
import jieba
import numpy as np
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')
import gensim
from keras.preprocessing.sequence import pad_sequences


class Prepare(object):
    def __init__(self, question_path, answer_path, word2vecpath, savepath):
        self.question_path = question_path
        self.answer_path = answer_path
        self.data_q = {}
        self.data_a = {}
        self.dict_q = []
        self.dict_a = []
        self.dict_w = []
        self.dict_id2w = []
        self.train_l = []
        self.train_r = []
        self.savepath = savepath
        self.word2vecpath = word2vecpath


class Read(Prepare):
    def read_q(self):
        f = open(self.question_path, encoding='utf-8')
        question = f.readlines()
        f.close()
        self.data_q = question
        print(len(question))

    def read_a(self):
        f = open(self.answer_path, encoding='utf-8')
        answer = f.readlines()
        f.close()
        self.data_a = answer
        print(len(answer))

    def process_w(self):
        data_q = self.data_q
        data_a = self.data_a
        dict_w = {}
        dict_w['UNK'] = 0
        dict_id2w = {}
        dict_id2w[0] = 'UNK'
        for i in range(len(self.data_q)):
            words = list(jieba.cut(data_q[i]+data_a[i]))
            for j, word in enumerate(words):
                if word not in dict_w:
                    dict_w[word] = len(dict_w)
                    dict_id2w[dict_w[word]] = word
        self.dict_w = dict_w
        self.dict_id2w = dict_id2w
        f = open(self.savepath+'/dict_w.txt', 'w', encoding='utf-8')
        f.write(str(dict_w))
        f.close()
        f = open(self.savepath+'/id2w.txt', 'w', encoding='utf-8')
        f.write(str(dict_id2w))
        f.close()

    def word2vec(self):
        dict_w = self.dict_w
        embedding_size = 64
        embedding_arry = np.zeros((len(dict_w) + 1, embedding_size))
        embedding_arry[0] = 0
        word2vec = gensim.models.KeyedVectors.load_word2vec_format(self.word2vecpath, binary=True)
        for index, word in enumerate(dict_w):
            if word in word2vec:
                embedding_arry[index] = word2vec[word]
        np.save(self.savepath+'/embedding.npy', embedding_arry)
        print('embedding shape:', embedding_arry.shape)

    def process_d_qa(self):
        data_q = self.data_q
        data_a = self.data_a
        dict_w = self.dict_w
        train_l = []
        train_r = []

        for i in range(len(data_q)):
            tem_l = []
            tem_r = []
            words_l = list(jieba.cut(data_q[i]))
            words_r = list(jieba.cut(data_a[i]))
            for j, word in enumerate(words_l):
                if j < 60 and len(word) < 10 and word != '\n':
                    tem_l.append(dict_w[word])
                else:
                    break
            for j, word in enumerate(words_r):
                if j < 60 and len(word) < 10 and word != '\n':
                    tem_r.append(dict_w[word])
                else:
                    break
            train_l.append(tem_l)
            train_r.append(tem_r)
        max_length = 0
        for line in train_r+train_l:
            if max_length < len(line):
                max_length = len(line)
        print('max length:', max_length)
        train_l = pad_sequences(train_l, maxlen=max_length)
        train_r = pad_sequences(train_r, maxlen=max_length)
        #print(train_l)
        self.train_r = train_r
        self.train_l = train_l
        np.save(self.savepath+'/train_r.npy', train_r)
        np.save(self.savepath+'/train_l.npy', train_l)


if __name__ == '__main__':
    read = Read('save/question.txt', 'save/answer_label.txt', 'F:/python/word2vec/src/tmp/new64.bin', 'save')
    read.read_q()
    read.read_a()

    read.process_w()
    read.word2vec()
    read.process_d_qa()
