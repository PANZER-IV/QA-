# -*- coding: utf-8 -*-

import unittest
from find import *
class TestMathFunc(unittest.TestCase):
    """Test mathfuc.py"""

    def test(self):
        user = QA(3, "question.txt", "answer.txt", "html_li.txt", "newquestion", "newanswer")
        user.get_data()
        user.rewrite()
        user.logo=3#查找
        for i in range(50):#50个正例
            user.question_message =user.X[i]
            get_i = user.find()
            self.assertEqual(5, user.logo_choose(user.logo, get_i))
        for i in range(50):#50个反例
            user.question_message = user.Y[i]
            get_i = user.find()
            self.assertEqual(6, user.logo_choose(user.logo, get_i))
        user.logo = 2#增加功能测试
        for i in range(50):#50个正例
            user.question_message = user.X[i]
            user.aswer_message=user.Y[i]
            get_i = user.find()
            self.assertEqual(3, user.logo_choose(user.logo, get_i))
        for i in range(50):#50个反例
            user.question_message = user.Y[i+555]
            user.aswer_message=user.X[i]
            get_i = user.find()
            self.assertEqual(4, user.logo_choose(user.logo, get_i))
        user.logo = 1#增加功能测试
        for i in range(50):#50个正例
            user.question_message = user.X[i]
            user.aswer_message=user.Y[i]
            get_i = user.find()
            self.assertEqual(1, user.logo_choose(user.logo, get_i))
        for i in range(50):#50个反例
            user.question_message = user.Y[i+555]
            user.aswer_message=user.X[i]
            get_i = user.find()
            self.assertEqual(2, user.logo_choose(user.logo, get_i))
        user.logo=0
        self.assertEqual(-1, user.logo_choose(user.logo, get_i))
        user.logo = "suiji"
        self.assertEqual(-1, user.logo_choose(user.logo, get_i))


if __name__ == '__main__':
    unittest.main()