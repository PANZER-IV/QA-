import keras
import numpy as np
from keras import Model
import os


#os.environ["CUDA_VISIBLE_DEVICES"] = "2"


train_l = np.load('save/train_l.npy')
train_r = np.load('save/train_r.npy')
model = keras.models.load_model('save/model_150.h5')

layer_name = 'lstm'
answer_vec = []
question_vec = []
max_length = len(train_l[0])
for i in range(len(train_r)):
    intermediate_layer_model = Model(inputs=model.input,
                                     outputs=[model.get_layer(layer_name).get_output_at(0),
                                              model.get_layer(layer_name).get_output_at(1)])
    intermediate_output = intermediate_layer_model.predict([np.asarray(train_l[i]).reshape(-1, max_length),
                                                            np.asarray(train_r[i]).reshape(-1, max_length)])
    question_vec.append(intermediate_output[0])
    answer_vec.append(intermediate_output[1])
    print(i)
np.save('save/answer_vec.npy', answer_vec)
np.save('save/question_vec.npy', question_vec)
