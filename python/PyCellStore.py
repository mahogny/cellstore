from jnius import autoclass

#PyCellStore = autoclass('cellstore.python.PyCellStore')
PyCellStore = autoclass('cellstore.r.RCellStore')

pcs = PyCellStore()

pcs.init('localhost',6667)

pcs.authenticate('mahogny','123')



pcs.upload_counts(X, 'mycounts')






#stack.push('hello')
#stack.push('world')

#print stack.pop() # --> 'world'
#print stack.pop() # --> 'hello'


#### https://pyjnius.readthedocs.io/en/latest/quickstart.html#a-minimal-example
