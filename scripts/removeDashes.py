import sys

if len(sys.argv) != 3:
    print "Usage: removeDashes.py <input> <output>"
    sys.exit(1)
inpFile = open(sys.argv[1], 'r')
outFile = open(sys.argv[2],'w')

line = inpFile.readline()
while line:
    query = line.split(' ',1)[1].replace("-"," ")
    outFile.write(line.split(' ',1)[0]+" "+query)
    line = inpFile.readline()
inpFile.close()
outFile.close()