import random

import numpy as np
import keras.backend as K
from keras import Input, Model
from keras.layers import Embedding, LSTM, Merge
from keras.optimizers import Adadelta
import os
#os.environ["CUDA_VISIBLE_DEVICES"] = "2"


n_hidden = 50
gradient_clipping_norm = 1.25
batch_size = 100
n_epoch = 150

train_l_r = np.load('save/train_l.npy')
train_r_r = np.load('save/train_r.npy')
embedding_arry = np.load('save/embedding.npy')
train_l = []
train_r = []
y_train = np.zeros((len(train_l_r)*10,))

for i in range(len(train_l_r)):
    train_l.append(train_l_r[i])
    train_r.append(train_r_r[i])
    for j in range(9):
        train_l.append(train_l_r[i])
        while True:
            z = random.randint(0, len(train_r_r)-1)
            if z != i:
                train_r.append(train_r_r[z])
                break
    y_train[i*10] = 1
'''''''''
print(train_l[0])
print(train_r[0])
print(y_train[0])
'''
embedding_size = 64

max_length = len(train_r_r[0])

print('max length:', max_length)

left_input = Input(shape=(max_length,), dtype='int32')
right_input = Input(shape=(max_length,), dtype='int32')

embedding_layer = Embedding(len(embedding_arry), embedding_size, weights=[embedding_arry], input_length=max_length,
                            mask_zero=False, trainable=True)

encoded_left = embedding_layer(left_input)
encoded_right = embedding_layer(right_input)

shared_lstm = LSTM(units=n_hidden, name='lstm')

left_output = shared_lstm(encoded_left)
right_output = shared_lstm(encoded_right)

malstm_distance = Merge(mode=lambda x: K.exp(-K.sum(K.abs(x[0] - x[1]), axis=1, keepdims=True)),
                        output_shape=lambda x: (x[0][0], 1))([left_output, right_output])

'''
malstm_distance = K.sigmoid(Dot(axes=-1, normalize=True)([left_output, right_output]))
'''
# model
malstm = Model([left_input, right_input], [malstm_distance])

optimizer = Adadelta(clipnorm=gradient_clipping_norm)

malstm.compile(loss='mean_squared_error', optimizer=optimizer, metrics=['accuracy'])
#train
malstm.fit(x=[np.asarray(train_l), np.asarray(train_r)], y=y_train, batch_size=batch_size, epochs=n_epoch,
           validation_data=([np.asarray(train_l), np.asarray(train_r)], y_train))

malstm.save('save/model.h5')
