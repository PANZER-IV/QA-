# coding=utf-8
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
from com.android.monkeyrunner.recorder import MonkeyRecorder as recorder
# 提供了根据ID进行访问
from com.android.monkeyrunner.easy import EasyMonkeyDevice
# 根据ID返回PyObject的方法
from com.android.monkeyrunner.easy import By
# 连接设备
device = MonkeyRunner.waitForConnection()
# 启动app
device.startActivity(component="com.example.a31284.chatqa/com.example.a31284.chatqa.LoginActivity")
easy_device = EasyMonkeyDevice(device)
MonkeyRunner.sleep(3)


def login():
    # 找到账号输入框id
    easy_device.touch(By.id('id/et_username'), MonkeyDevice.DOWN_AND_UP)
    # 输入账号
    device.type('user1')
    MonkeyRunner.sleep(1)

    # 找到密码输入框id
    easy_device.touch(By.id('id/et_password'), MonkeyDevice.DOWN_AND_UP)
    # 输入密码
    device.type('123456')
    MonkeyRunner.sleep(1)

    # 点击登录按钮
    easy_device.touch(By.id('id/bt_login'), MonkeyDevice.DOWN_AND_UP)
    MonkeyRunner.sleep(6)


def chat():
    easy_device.touch(By.id('id/chat_input_message'), MonkeyDevice.DOWN_AND_UP)
    device.type('云容器引擎')
    MonkeyRunner.sleep(1)

    easy_device.touch(By.id('id/chat_send"'), MonkeyDevice.DOWN_AND_UP)
    MonkeyRunner.sleep(10)

    easy_device.touch(By.id('id/chat_input_message'), MonkeyDevice.DOWN_AND_UP)
    device.type('宇宙的目的')
    MonkeyRunner.sleep(1)

    easy_device.touch(By.id('id/chat_send"'), MonkeyDevice.DOWN_AND_UP)
    MonkeyRunner.sleep(10)


def upload():
    easy_device.touch(By.id('id/filePicker'), MonkeyDevice.DOWN_AND_UP)
    MonkeyRunner.sleep(1)

    easy_device.touch(By.id('id/apkPathText"'), MonkeyDevice.DOWN_AND_UP)
    # device.type('宇宙的目的') 路径
    MonkeyRunner.sleep(10)

    easy_device.touch(By.id('id/chat_input_message'), MonkeyDevice.DOWN_AND_UP)
    device.type('宇宙的目的')
    MonkeyRunner.sleep(1)

    easy_device.touch(By.id('id/upload"'), MonkeyDevice.DOWN_AND_UP)
    MonkeyRunner.sleep(10)


def train():
    easy_device.touch(By.id('id/train'), MonkeyDevice.DOWN_AND_UP)
    MonkeyRunner.sleep(1)


def qamanager():
    easy_device.touch(By.id('id/input_question'), MonkeyDevice.DOWN_AND_UP)
    device.type('云容器引擎')
    MonkeyRunner.sleep(1)

    easy_device.touch(By.id('id/input_answer"'), MonkeyDevice.DOWN_AND_UP)
    device.type('宇宙的目的')
    MonkeyRunner.sleep(10)

    easy_device.touch(By.id('id/input_answer'), MonkeyDevice.DOWN_AND_UP)
    device.type('目前CTS仅支持部分ECS、EVS、VBS、IMS、AS、CES和VPC的操作通过资源ID跳转到对应云资源的详情页面，该功能正在逐步完善。')
    MonkeyRunner.sleep(1)

    easy_device.touch(By.id('id/delete"'), MonkeyDevice.DOWN_AND_UP)
    MonkeyRunner.sleep(10)


if __name__ == '__main__':
    login()
    # chat()
    # upload()
    # train()
    # qamanager()
