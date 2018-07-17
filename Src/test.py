import datetime
import random
import Src.predict2

start_time = datetime.datetime.now()
f = open('save/question.txt', encoding='utf-8') 
fold = f.readlines()
f.close()
number =20
random.shuffle(fold)
fold
acc = 0
for i in range(number):
    predict = Src.predict2.Predict(fold[i])
    tem = predict.predict_test()
    if tem == 'true':
        acc = acc+1
        print(acc)
print('正例数为：', number)
print('正例准确率为：', acc/number)
f = open('save/mistake.txt', encoding='utf-8')
mistake = f.readline()
f.close()
acc2 = 0
for string in mistake:
    predict = Src.predict2.Predict(string)
    tem = predict.predict_test()
    if tem == 'false':
        acc = acc+1
        acc2 =acc2 +1
        print(acc2)
print('负例数为：', len(mistake))
print('负例准确率为：', acc2/len(mistake))
print('样例数为：', number+len(mistake))
print('准确率为：', acc/(number+len(mistake)))
end_time = datetime.datetime.now()
print((end_time - start_time).seconds,'s')
