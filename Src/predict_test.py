import jieba
import keras
import numpy as np
import keras.backend as K
import os


os.environ["CUDA_VISIBLE_DEVICES"] = "2"

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
max_length = 0
for line in train_r+train_l:
    if max_length < len(line):
        max_length = len(line)
print('max length:', max_length)

tem = 0
mark = 0
end = []
for i in range(max_length-len(words)):
    words.insert(0, 0)

layer_name = 'lstm'
answer_vec = []
'''''''''
for i in range(len(train_r)):
    intermediate_layer_model = Model(inputs=model.input,
                                     outputs=model.get_layer(layer_name).get_output_at(1))
    intermediate_output = intermediate_layer_model.predict([np.asarray(words).reshape(-1, max_length), np.asarray(train_r[i]).reshape(-1, max_length)])
    answer_vec.append(intermediate_output)
    print(i)
np.save('save/answer_vec.npy', answer_vec)
'''
'''
intermediate_layer_model = Model(inputs=model.input, outputs=model.get_layer(layer_name).get_output_at(1))
intermediate_output = intermediate_layer_model.predict([np.asarray(words).reshape(-1, max_length),
                                                        np.asarray(train_r[0]).reshape(-1, max_length)])
print(intermediate_output)
'''''''''

for i in range(len(train_l)):
    t = model.predict([np.asarray(words).reshape(-1, max_length), np.asarray(train_r[i]).reshape(-1, max_length)])
    if t > tem:
        tem = t
        print(tem)
        print(answer[i])
        mark = i
print(mark)
print(answer[mark])

'''''''''
acc = 0
for j in range(100):
    tem = 0
    for i in range(100):
        t = model.predict([np.asarray(train_r[j]).reshape(-1, max_length), np.asarray(train_r[i]).reshape(-1, max_length)])
        if t > tem:
            tem = t
            mark = i
    if mark == j:
        acc = acc + 1
print(acc/100)
'''