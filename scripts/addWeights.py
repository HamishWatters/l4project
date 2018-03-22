import sys

if len(sys.argv) != 2:
    print "Usage: addWeights qrel-file"
    sys.exit()
qrel_file = open(sys.argv[1], 'r')
topic_file = open(sys.argv[1] + '_weighted.topic', 'w')
line = qrel_file.readline()
while line:
    query = line.split(" ")[0]
    oldquery=query
    x = query.replace("%20"," ").replace("%22" , " ").replace("."," ").replace(":","").split("/")
    weighted_query = ""
    for word in x[0].split(" "):
        weighted_query += word + "^1.2 "
    for i in range(1,len(x)-1):
        for word in x[i].split(" "):
            weighted_query += word + "^" + str(1-float(i)/10) +" "
    for word in x[len(x)-1].split(" "):
        weighted_query += word + "^1.55 "

    topic_file.write(query + " " + weighted_query + '\n')
    line = qrel_file.readline()
    while line.split(" ")[0] == oldquery:
        line = qrel_file.readline()

qrel_file.close()
topic_file.close()
