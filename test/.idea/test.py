import re

key = r"<span44444444>"#源文本
p1 = r"<span?.+>"#我们写的正则表达式，下面会将为什么
pattern1 = re.compile(p1)
print (pattern1.findall(key))#发没发现，我怎么写成findall了？咋变了呢？