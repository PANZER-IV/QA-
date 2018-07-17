import jieba
import keras
import numpy as np
'''''''''
import os


os.environ["CUDA_VISIBLE_DEVICES"] = "2"
'''
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
string = '应用场景容器镜像仓库'
words = list(jieba.cut(string))
for i, word in enumerate(words):
    words[i] = int(dict_w[word])
train_l = np.load('save/train_l.npy')
train_r = np.load('save/train_r.npy')
model = keras.models.load_model('save/model_150.h5')
max_length = len(train_l[0])
print('max length:', max_length)

acc = 0
for i in range(max_length-len(words)):
    words.insert(0, 0)
'''''''''
layer_name = 'lstm'
answer_vec = []

for i in range(len(train_l)):
    intermediate_layer_model = Model(inputs=model.input,
                                     outputs=model.get_layer(layer_name).get_output_at(0))
    intermediate_output = intermediate_layer_model.predict([np.asarray(train_l[i]).reshape(-1, max_length), 
                                                            np.asarray(words).reshape(-1, max_length)])
    answer_vec.append(intermediate_output)
    print(i)
np.save('save/question_vec.npy', answer_vec)
'''

answer_vec = np.load('save/answer_vec.npy')
question_vec = np.load('save/question_vec.npy')
number = len(answer_vec)

'''''''''
t = np.exp(-np.sum(np.abs(question_vec[1000][0] - answer_vec[2000][0])))
print(model.predict([np.asarray(train_l[0]).reshape(-1, max_length),
                                                            np.asarray(train_r[0]).reshape(-1, max_length)]))
print(t)
'''

for j in range(number):
    mark = 0
    tem = 0
    for i in range(number):
        t = np.exp(-np.sum(np.abs(question_vec[j][0] - answer_vec[i][0])))
        if t > tem:
            tem = t
            mark = i

    if mark == j:
        print(mark+1)
        acc = acc +1

print(acc/number)

'''''
tem = 0
mark = 0
for i in range(number):
    t = np.exp(-np.sum(np.abs(question_vec[1][0] - answer_vec[i][0])))
    if t > tem:
        tem = t
        mark = i
        print(mark)
'''