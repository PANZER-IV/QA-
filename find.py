import sys
class QA(object):
    def __init__(self, logo,question_address,answer_address,html_address,question_message,answer_message):
        self.logo=logo
        self.question_address=question_address
        self.answer_address=answer_address
        self.html_address=html_address
        self.question_message=question_message
        self.answer_messgae=answer_message
        self.X=[]
        self.Y=[]
        self.Z=[]
    def get_data(self):
        question = open(self.question_address, 'r+', encoding='utf-8')
        answer = open(self.answer_address, 'r+', encoding='utf-8')
        html = open(self.html_address, 'r+', encoding='utf-8')
        for line in question:
            self.X.append(line.strip())
        for line in answer:
            self.Y.append(line.strip())
        for line in html:
            self.Z.append(line.strip())
        question.seek(0)
        question.truncate()
        answer.seek(0)
        answer.truncate()
        html.seek(0)
        html.truncate()
        question.close()
        answer.close()
        html.close()
    def find(self):
        for i in range(len(self.X)):
            if self.X[i]==self.question_message:
                return i
        return -1
    #删除操作
    def delete(self,i):
        self.X.remove(self.X[i])
        self.Y.remove(self.Y[i])
        self.Z.remove(self.Z[i])
    #增加操作
    def add_data(self):
        self.X.append(self.question_message)
        self.Y.append(self.answer_messgae)
        self.Z.append("无")
    #重新得到文档
    def rewrite(self):
        question = open(self.question_address, 'r+', encoding='utf-8')
        answer = open(self.answer_address, 'r+', encoding='utf-8')
        html = open(self.html_address, 'r+', encoding='utf-8')
        for i in range(len(self.X)):
            question.write(self.X[i] + '\n')
            answer.write(self.Y[i]+ '\n')
            html.write(self.Z[i]+'\n')
if __name__ == '__main__':

    user = QA(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6])
    #user=QA(sys.argv[1],"question.txt","answer.txt","html_li.txt","newquestion","newanswer")
    user.get_data()
    get_i=user.find()

    if user.logo=='1':
        if get_i!=-1:
            user.delete(get_i)
            print("删除成功")
        else:
             print("QA对不存在")
    elif user.logo == '2':
        print('begin')
        if get_i!=-1:
            print("QA对已存在")
        else:
            user.add_data()
            print("增加成功")
    elif user.logo=='0':
        if get_i!=-1:
            print("Q：" + user.question_message)
            print("A:" + user.Y[get_i])
        else:
            print("问题不存在")
    user.rewrite()