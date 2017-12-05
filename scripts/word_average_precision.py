import sys

if len(sys.argv) < 2:
    print "Usage: word_average_precision.py inputfile outputfile"
    sys.exit()
inFile = sys.argv[1]
outFile = sys.argv[2]
queryPrecision = open(inFile,'r')
wordPrecision = open(outFile,'w')

line = queryPrecision.readline()
wordScores = {}
while line:
    row = line.split(",")
    query = row[0]
    score = row[1].replace("\r\n","")
    query = query.replace("%20"," ").replace("%22"," ").replace("/"," ").lower()
    words = query.split(" ")
    for word in words:
        if word in wordScores:
            wordScores[word] += [score]
        else:
            wordScores[word] = [score]
    line = queryPrecision.readline()
for key in wordScores:
    total = 0
    n = 0
    for score in wordScores[key]:
        total += float(score)
        n += 1
    wordPrecision.write(key+","+str(total/n)+","+str(n)+'\n')
