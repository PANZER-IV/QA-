import numpy as np
import keras

train_l = np.load('train_l.npy')
train_r = np.load('train_r.npy')
embedding_arry = np.load('embedding_arry.npy')

print (embedding_arry[6])

embedding_size =64

max_length =244
model = keras.Sequential()
model.add(keras.layers.Embedding(len(embedding_arry), embedding_size, weights=[embedding_arry], input_length=1,
                                 trainable=True))
# the model will take as input an integer matrix of size (batch, input_length).
# the largest integer (i.e. word index) in the input should be no larger than 999 (vocabulary size).
# now model.output_shape == (None, 10, 64), where None is the batch dimension.

input_array = train_l[0]

model.compile('rmsprop', 'mse')
output_array = model.predict(input_array)
print(output_array)
