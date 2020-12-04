#importing necessary packages for string manipulation
library("gtools")
library("stringi")
library("pracma")

#kmer length
kmer_len <- 3

#function to break a string to kmers
#passing the string and the length of each kmer
kmer<-function(dna_seq,k)
{
  #list to store the kmers
  kmers<-NULL
  #iterating through the string
  for (i in 1:(nchar(dna_seq)-k+1))
  {  
    #breaking into kmers and appending it to the list
    kmers<-cbind(c(kmers,substring(dna_seq,i,(i+k-1))))
    
  }
  return(kmers)
}

#function to sort the list of kmers in lexicographically order
sort_kmers<-function(kmers)
{
  #performing bubble sort 
  for (i in 1:(length(kmers)-1) )
  {
    for (j in 1:(length(kmers)-i) )
    {
      #lexicographically comparing the string
      if (stri_cmp(kmers[j],kmers[j+1])>0) { 
        #swapping
        temp<-kmers[j]
        kmers[j]<-kmers[j+1]
        kmers[j+1]<-temp
        
      }
    }
  }
  return (kmers) 
}

#function to retrieve starting word.
kmers<-kmer("AGGACGAA",kmer_len)
temp<-sort_kmers(kmers)

#De Bruijn function
De_bruijn<-function(a) {
  suffix<-NULL
  prefix<-NULL
  edges<-NULL
  nodes<-NULL


  for ( i in 1:(length(a))){
    n1<-(nchar(a[i]))
    suffix<-cbind(suffix,substring(a[i],2,n1))
    prefix<-cbind(prefix,substring(a[i],1,n1-1))
  }
  for ( j in 1:(length(a))){
    edges<-rbind(edges,c(prefix[j],suffix[j]))
    nodes<-cbind(nodes,prefix[j],suffix[j])
    
  }
  temp<-NULL
  foo <- vector(mode="list", length=length(unique(c(prefix))))
  names(foo) <- unique(c(prefix))
  for (i in 1:(length(a))){
 
   temp<-cbind(temp,prefix[i])
  if(is.na(match(prefix[i],temp)))
     {
  foo[[i]] <-c(suffix[i]);
  }
else
{
  foo[[match(prefix[i],temp)]] <-c(foo[[match(prefix[i],temp)]],suffix[i]);
}
  }
  print(suffix)
  nodes<-unique(c(nodes))
  print(nodes)
  print(foo)
  
}
#assuming all the kmers as start and trying to construct the string
#sorting the kmer

print(kmers)
print(temp)
print(De_bruijn(kmers))