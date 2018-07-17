# -*- coding: utf-8 -*-

import unittest
from find import *
class Test_blank(unittest.TestCase):

    def test(self):
        user = QA(3, "question.txt", "answer.txt", "html_li.txt", "newquestion", "newanswer")
        user.get_data()
        user.rewrite()
        user.logo = 0
        self.assertEqual(-1, user.logo_choose(user.logo, get_i))
        user.logo = "suiji"
        self.assertEqual(-1, user.logo_choose(user.logo, get_i))
        user.logo = 999
        self.assertEqual(-1, user.logo_choose(user.logo, get_i))
        user.logo = " "
        self.assertEqual(-1, user.logo_choose(user.logo, get_i))

if __name__ == '__main__':
    unittest.main()


