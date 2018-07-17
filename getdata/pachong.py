
# coding=utf-8
import sys
import re
import urllib.request
import os
import copy

#返回html格式
question_data=[]
answer_data=[]
html_li=[]
def delblankline(infile, outfile):
    infopen = open(infile, 'r',encoding='UTF-8')
    outfopen = open(outfile, 'w',encoding='UTF-8')
    lines = infopen.readlines()
    for line in lines:
        if line.split():
            outfopen.writelines(line)
        else:
            outfopen.writelines("")
    infopen.close()
    outfopen.close()
def getInfo(FilePath):
    HtmlData = []
    RetArray = []
    Head_Data = []
    with open(FilePath, mode='r', encoding='utf-8') as file:
        while True:
            Data = file.readline().strip()
            if Data == '':
                break
            HtmlData.append(Data)
        for ix in range(len(HtmlData)):
            if(HtmlData[ix].startswith('<h')):
                Head_singleline = HtmlData[ix]
                Head_num = HtmlData[ix][2]
                while True:
                    if '</h{}>'.format(Head_num) in Head_singleline:
                        break
                    ix+=1
                    if HtmlData[ix] == ('</h{}>'.format(Head_num)):
                        Head_singleline += HtmlData[ix]
                        break
                    else:
                        Head_singleline += HtmlData[ix]
                p1 = re.compile(r'<h.+?>(.*)<', re.S)
                Head_infoOnly = re.findall(p1, Head_singleline)
                Head_Data.append(''.join(Head_infoOnly))
                Head_Data.append('')
                while True:
                    ix+=1
                    if ix >= len(HtmlData) or HtmlData[ix].startswith('<h'):
                        break
                    if HtmlData[ix].startswith('<p'):
                        P_Singleline = HtmlData[ix]
                        while True:
                            ix+=1
                            if '</p>' in P_Singleline or ix >= len(HtmlData):
                                ix-=1
                                break
                            P_Singleline+=HtmlData[ix]
                        p2 = re.compile(r'<p.+?>(.*)<', re.S)
                        P_infoOnly = re.findall(p2, P_Singleline)
                        Head_Data[1]+=''.join(P_infoOnly).replace(' ','')
                        continue
                    if HtmlData[ix].startswith('<li'):
                        Li_Singleline = HtmlData[ix]
                        while True:
                            ix += 1
                            if '</li>' in Li_Singleline or ix >= len(HtmlData):
                                ix-=1
                                break
                            Li_Singleline+=HtmlData[ix]
                        p3 = re.compile(r'<l.+?>(.*)<', re.S)
                        Li_infoOnly = re.findall(p3, Li_Singleline)
                        Head_Data[1]+=''.join(Li_infoOnly).replace(' ','')
                        continue
                RetArray.append(copy.deepcopy(Head_Data))
                Head_Data.clear()
    return  RetArray

i=1
#print(X[2][1]+"[lable]"+str(i))
def answer_cheat(X,i):
    #如果内容数组里有标题类
    if '<h'in X[i][1]:
        p1 = re.compile(r'<h.+?>.*?</p+.?>', re.S)
        message1 = re.findall(p1, X[i][1])
        if message1:
            for j in range(len(message1)):
                X[i][1]=X[i][1].replace(''.join(message1[j]),'')
            p2 = re.compile(r'<h.*?>(.*?)</h.*?>', re.S)
            p3 = re.compile(r'<p.*?>(.*?)</p>', re.S)
            for i in range(len(message1)):
                message2=''.join(re.findall(p2,message1[i]))
                message3 = ''.join(re.findall(p3,message1[i]))
                X.append([message2,message3])
        p1 = re.compile(r'<h.+?>.*?</li+.?>', re.S)
        message1 = re.findall(p1, X[i][1])
        if message1:
            for j in range(len(message1)):
                X[i][1] = X[i][1].replace(''.join(message1[j]), '')
            p2 = re.compile(r'<h.*?>(.*?)</h.*?>', re.S)
            p3 = re.compile(r'<li.*?>(.*?)</li>', re.S)
            for i in range(len(message1)):
                message2 = ''.join(re.findall(p2, message1[i]))
                message3 = ''.join(re.findall(p3, message1[i]))
                # print(message2)
                # print(message3)
                X.append([message2, message3])
        p1 = re.compile(r'<h.+?>.*?</h+.?>', re.S)
        message1 = re.findall(p1, X[i][1])
        if message1:
            for j in range(len(message1)):
                X[i][1] = X[i][1].replace(''.join(message1[j]), '')
    #如果内容数组里有其它标签
    elif '<p' in X[i][1] or'<li' in X[i][1] or'<em' in X[i][1] or'<u' in X[i][1]:
        p4= re.compile(r'<p.*?>', re.S)
        message_p1 = re.findall(p4, X[i][1])
        p5 = re.compile(r'</p>', re.S)
        message_p2 = re.findall(p5, X[i][1])
        p6 = re.compile(r'<li.*?>', re.S)
        message_p3 = re.findall(p6, X[i][1])
        p7 = re.compile(r'</li>', re.S)
        message_p4 = re.findall(p7, X[i][1])
        p8 = re.compile(r'<em.*?>', re.S)
        message_p5 = re.findall(p8, X[i][1])
        p9 = re.compile(r'</em>', re.S)
        message_p6 = re.findall(p9, X[i][1])
        p10 = re.compile(r'<u.*?>', re.S)
        message_p7 = re.findall(p10, X[i][1])
        p11 = re.compile(r'</u.*?>', re.S)
        message_p8 = re.findall(p11, X[i][1])
        p12 = re.compile(r'<o.*?>', re.S)
        message_p9 = re.findall(p12, X[i][1])
        message=message_p1+message_p2+message_p3+message_p4+message_p5+message_p6+message_p7+message_p8+message_p9
        for elem in message:
            X[i][1] = X[i][1].replace(elem, '')
    p= re.compile(r'<.*?>', re.S)
    message = re.findall(p, X[i][1])
    for elem in message:
        X[i][1] = X[i][1].replace(elem, '')
    X[i][1] = X[i][1].replace("：", ":")
    p1 = re.compile(r'Q:(.+?)A:', re.S)
    message1 = re.findall(p1, X[i][1])
    if message1:
        p2 = re.compile(r'A:(.+?)Q:', re.S)
        message2 = re.findall(p2, X[i][1])
        message = message1 + message2
        for item in message:
            X[i][1] = X[i][1].replace(item, '')
        X[i][1] = X[i][1].replace('Q:A:', '')
        message2.append(X[i][1])
        for i in range(len(message2)):
            X.append([message1[i], message2[i]])
def remove_same_itemQuestion(X,i,li):
    if X[i][0] not in question_data:
        if len(X[i][1])>3:
            question_data.append(X[i][0].strip())
            answer_data.append(X[i][1].strip())
            html_li.append(li)
def add_lable(li):
    FilePath = r'data2.txt'
    X = getInfo(FilePath)
    h=''
    #print(X)
    i=0
    while True:
        if i>=len(X):
            break
        if  X[i][1]=='':
            h=X[i][0]
            X.remove(X[i])
            continue
        X[i][0] = h + X[i][0]
        i+=1
    i = 0
    while True:
        if i>=len(X):
            break
        answer_cheat(X,i)
        remove_same_itemQuestion(X,i,li)
        i+=1
def get_data(question,answer,answer_lable,html):
    for i in range(len(question_data)):
        question.write(question_data[i] + '\n')
        answer.write(answer_data[i] + '\n')
        answer_lable.write(question_data[i]+answer_data[i] + '\n')
        html.write(html_li[i]+'\n')
def GetFileList(dir, fileList):
    newDir = dir
    if os.path.isfile(dir):         # 如果是文件则添加进 fileList
        fileList.append(dir)
    elif os.path.isdir(dir):
        for s in os.listdir(dir):   # 如果是文件夹
            newDir = os.path.join(dir, s)
            GetFileList(newDir, fileList)
    return fileList
def gethtml(url):
  page=urllib.request.urlopen(url)
  html=page.read()
  return html
#识别表格
def move_table(html):
  p = re.compile(r'(<table.*?</table>)', re.S)
  message=re.findall(p,html)
  message = re.findall(p, html)
  #message = ''.join(message)
  #print (message)
  return message
def move_a(html):
  p2 = re.compile(r'(<a.+?</a>)', re.S)
  message = re.findall(p2, html)
  #message = ''.join(message)
  #print message
  return message
def move_span(html):
  p1 = re.compile(r'(</spa.+?>)', re.S)
  message1 = re.findall(p1, html)
  p2 = re.compile(r'(<spa.+?>)', re.S)
  message2 = re.findall(p2, html)
  p3 = re.compile(r'(<di.+?>)', re.S)
  message3 = re.findall(p3, html)
  p4 = re.compile(r'(</di.+?>)', re.S)
  message4 = re.findall(p4, html)
  p5 = re.compile(r'(<stron.+?>)', re.S)
  message5 = re.findall(p5, html)
  p6 = re.compile(r'(</stron.+?>)', re.S)
  message6 = re.findall(p6, html)
  p7 = re.compile(r'(<b.+?>)', re.S)
  message7 = re.findall(p7,html)
  p8= re.compile(r'(</b>)', re.S)
  message8 = re.findall(p8, html)
  p9 = re.compile(r'(<pr.+?>)', re.S)
  message9 = re.findall(p9, html)
  p10 = re.compile(r'(</pr.+?>)', re.S)
  message10 = re.findall(p10, html)
  p11 = re.compile(r'(<hr />)', re.S)
  message11 = re.findall(p11, html)
  p12 = re.compile(r'(<i.+?>)', re.S)
  message12 = re.findall(p12, html)
  p13 = re.compile(r'(</i>)', re.S)
  message13 = re.findall(p13, html)
  p14 = re.compile(r'(<u.+?>)', re.S)
  message14 = re.findall(p14, html)
  p15 = re.compile(r'(</u.*?>)', re.S)
  message15 = re.findall(p15, html)
  message=message1+message2+message3+message4+message5+message6+message7+message8+message9+message10+message11+message12+message13+message14+message15
  return message
def remove(message,html):
  get_message = html
  for elem in message:
    get_message = get_message.replace(elem, '')
  #print(get_message)
  return get_message
def getmessage(html,fp):
  html = html.decode('utf-8')
  p2 = re.compile(r'<div class="helpContent">(.*)<div class="help-detail-feedback">',re.S)
  message=re.findall(p2,html)
  if not message :
    p2 = re.compile(r'<div id="content"(.*)<div class="register-area">',re.S)
    message = re.findall(p2, html)
  message = ''.join(message)
  # 将结果转换为字符串类型
  message = str(message)
  table_message=move_table(message)
  message=remove(table_message,message)
  a_message = move_a(message)
  message = remove(a_message, message)
  span_message = move_span(message)
  message = remove(span_message, message)
  #print message1
  fp.writelines(message+ '\n')
 #返回正则匹配的结果
  return message
def go(li):
    fp=open('data.txt','w+',encoding='utf-8')
    #web=gethtml('file:///C:/Users/LUO/Desktop/support.huaweicloud.com/developer.huaweicloud.com_tools_ide.html')
    text="file:///"
    web=gethtml(text+li)
    message=getmessage(web,fp)
    fp.close()
    delblankline("data.txt", "data2.txt")
if __name__=='__main__':
    question = open('question.txt', 'w+', encoding='utf-8')
    answer = open('answer.txt', 'w+', encoding='utf-8')
    answer_lable = open('answer_label.txt', 'w+', encoding='utf-8')
    html = open('html_li.txt', 'w+', encoding='utf-8')
    lable_num=0
    i=0
    dir = "C:\\Users\\LUO\\Desktop\\support.huaweicloud.com"
    list = GetFileList(dir, [])
    for li in list:
        go(li)
        add_lable(li)
        i+=1
        print(i)

    get_data(question, answer, answer_lable,html)
