library(rJava) 

#####################################################################
## Connect to cellstore
cellstore.connect <- function(address="localhost", port=12649){
  .jinit(force.init = TRUE, 
         classpath="/home/mahogny/javaproj/cellstore/bin",
         parameters="-Xmx512m")
  ob <- .jnew("cellstore.r.RCellStore")  
  ret <- .jcall(ob, "Z", "init", address, as.integer(port))
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
  ret
}


#####################################################################
## List count tables
cellstore.list.clustering <- function(conn){
  ob <- .jcall(conn, "Lcellstore/r/RDataFrame;", "listClustering")
  cn <- .jcall(ob, "[S", "getNames")
  nr <- .jcall(ob, "I", "getNumRow")
  data <- .jcall(ob, "[Ljava/lang/Object;","getColumns")
  m <- data.frame(.temp=1:nr)
  for(i in 1:length(cn)) {
    m[,cn[i]] <- .jevalArray(data[[i]])
  }
  m[,-1]
}


cc <- cellstore.connect()
cellstore.list.clustering(cc)



#####################################################################
## Upload a clustering object
cellstore.upload.clustering <- function(conn, X, name){
  
}

#####################################################################
## Upload a connectivity object
cellstore.upload.connectivity <- function(conn, X, name){
  
}

cellstore.wrap.matrix.int <- function(m){
  .jnew("cellstore/r/RMatrixI",as.integer(as.vector(m)), dim(m))
}
cellstore.wrap.matrix.double <- function(m){
  .jnew("cellstore/r/RMatrixD",as.double(as.vector(m)), dim(m))
}
cellstore.wrap.matrix.sparse.double <- function(values, indices, indptr){
  .jnew("cellstore/r/CSRMatrixD",as.double(values), indices, indptr)
}


#http://www.scipy-lectures.org/advanced/scipy_sparse/csr_matrix.html

#####################################################################
## Upload a projection
cellstore.upload.projection <- function(conn, cell_pos, name, relatedto){
  cell_id <- cbind(
    rep(relatedto, nrow(cell_pos)),
    1:nrow(cell_pos)) #should technically use obs$index
  
  
  ret <- .jcall(conn, "Z", "uploadProjection", 
                cellstore.wrap.matrix.int(cell_id),
                cellstore.wrap.matrix.double(cell_pos),
                name, relatedto)
  
}




m <- matrix(c(1,2,3,4,5,6),nrow=2)
as.vector(m)

cc <- cellstore.connect()

cellstore.list.clustering(cc)


# cellstore.upload.count(cc, X, "mycount")
# cellstore.upload.projection(cc, m, "mycount")
# 
# 
# cellstore.upload.projection(cc, m, "tsne", "2")   
# 
# 
# cellstore.upload.connectivity(cc, m, "jpdist", "")
# 
# 
# #cc
# 
# #ret <- .jcall("cellstore.r.RCellStore", "cellstore.r.RCellStore", "connect", address, port, user, password)
# #ret <- .jcall("cellstore.r.Test", "[D", "test")
# #  z=.jcall("cellstore.r.RCellStore", "[D", "convolve", x,y)
# 
# 
# 
# 
