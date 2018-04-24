library(rJava) 

#####################################################################
## Connect to cellstore
cellstore.connect <- function(address="localhost", port=6666){
  # user="mahogny"
  # password="123"
  .jinit(force.init = TRUE, 
         classpath="/home/mahogny/javaproj/cellstore/bin",
         parameters="-Xmx512m")
  ob <- .jnew("cellstore.r.RCellStore")  
  ret <- .jcall(ob, "Z", "init", user, password)
  if(ret)
    return(ob)
  else
    return(NULL)
}


#####################################################################
## Authenticate connection
cellstore.authenticate <- function(conn, user="mahogny", password="123"){
  ret <- .jcall(conn, "Z", "authenticate", user, password)
  return(ret)
}




#####################################################################
## Upload a count object
cellstore.upload.count <- function(conn, X, name){
  ret <- .jcall(conn, "Z", "uploadCount", X, name)
  
}

cc <- cellstore.connect()
cellstore.upload.count(cc, X, "mycount")




#####################################################################
## Upload a clustering object
cellstore.upload.clustering <- function(conn, X, name){
  
}

#####################################################################
## Upload a connectivity object
cellstore.upload.connectivity <- function(conn, X, name){
  
}


#####################################################################
## Upload a projection
cellstore.upload.projection <- function(conn, X, name){
  
}







#ret <- .jcall("cellstore.r.RCellStore", "cellstore.r.RCellStore", "connect", address, port, user, password)
#ret <- .jcall("cellstore.r.Test", "[D", "test")
#  z=.jcall("cellstore.r.RCellStore", "[D", "convolve", x,y)

