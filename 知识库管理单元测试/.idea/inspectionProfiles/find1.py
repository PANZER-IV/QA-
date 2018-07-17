from find import *
user=QA(2,"question.txt","answer.txt","html_li.txt","newquestion","newanswer")
user.get_data()
get_i = user.find()
user.logo_choose(user.logo, get_i)
user.rewrite()