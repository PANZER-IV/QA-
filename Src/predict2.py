import jieba
import keras
import numpy as np
from keras import Model
import os


#os.environ["CUDA_VISIBLE_DEVICES"] = "2"

class Predict(object):
    def __init__(self, string):
        self .string = string

    def predict(self):
        f = open('save/answer.txt', encoding='utf-8')
        answer = f.readlines()
        f.close()
        f = open('save/dict_w.txt', 'r', encoding='utf-8')
        a = f.read()
        dict_w = eval(a)
        f.close()
        f = open('save/id2w.txt', 'r', encoding='utf-8')
        a = f.read()
        id2w = eval(a)
        f.close()
        string = self.string
        words = list(jieba.cut(string))
        for i, word in enumerate(words):
            if dict_w.get(word)is not None:
                words[i] = int(dict_w[word])
            else:
                words[i] = 0
        train_l = np.load('save/train_l.npy')
        train_r = np.load('save/train_r.npy')
        model = keras.models.load_model('save/model_150.h5')
        max_length = len(train_l[0])
        tem = 0
        mark = 0

        for i in range(max_length-len(words)):
            words.insert(0, 0)

        layer_name = 'lstm'
        answer_vec = np.load('save/answer_vec.npy')
        intermediate_layer_model = Model(inputs=model.input,
                                         outputs=model.get_layer(layer_name).get_output_at(0))
        intermediate_output = intermediate_layer_model.predict([np.asarray(words).reshape(-1, max_length),
                                                                np.asarray(train_r[0]).reshape(-1, max_length)])
        for i in range(len(answer_vec)):
            t = np.exp(-np.sum(np.abs(intermediate_output[0] - answer_vec[i][0])))
            if t > tem:
                tem = t
                mark = i
        print(mark+1)
        if tem <= 0.5 or mark == 2425:
            print('请重新输入问题')
        else:
            print(answer[mark])


if __name__ == '__main__':
    predict = Predict('')
    predict.predict()
