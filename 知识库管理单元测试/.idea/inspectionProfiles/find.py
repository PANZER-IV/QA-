
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
        question.close()
        answer.close()
        html.close()

    def logo_choose(self,logo,get_i):
        if logo == 1:
            if get_i != -1:
                self.delete(get_i)
                #print("删除成功")
                return 1
            else:
               # print("QA对不存在")
                return 2
        elif logo == 2:
            if get_i != -1:
                #print("QA对已存在")
                return 3
            else:
                self.add_data()
                #print("增加成功")
                return 4
        elif logo == 3:
            if get_i != -1:
                #print("Q：" + self.question_message)
                #print("A:" + self.Y[get_i])
                return 5
            else:
                #print("问题不存在")
                return 6
        else:
            return -1
if __name__ == '__main__':


    user=QA(3,"question.txt","answer.txt","html_li.txt","newquestion","newanswer")
    user.get_data()
    user.question_message = "DevOps解决方案"
    get_i=user.find()
    user.logo_choose(user.logo,get_i)
    user.rewrite()