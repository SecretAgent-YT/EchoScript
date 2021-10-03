system:print('Demo testing')

import test

system:print(test.imorss)

var x = 50
var y = 50
var final = x + y
var hello = 'hello to you too'
func;test{
system:print('testing')
system:print(final)
}
func;testone{
system:print('testone')
system:print(hello)
}
func:test
func:testone
func:test.test