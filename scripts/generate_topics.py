import sys

if len(sys.argv) != 2:
    print "Usage: generate_topics qrel-file"
    sys.exit()
qrel_file = open(sys.argv[1], 'r')
topic_file = open(sys.argv[1] + '.topic', 'w')
line = qrel_file.readline()
while line:
    query = line.split(" ")[0]
    oldquery=query
    qsplit = query.replace("%20"," ").replace("%22" , " ").replace("."," ").replace(":","").split("/")

    topic_file.write(query + " " + query.replace("/"," ").replace("%20"," ").replace("%22" , " ").replace("."," ").replace(":","") + '\n')
    line = qrel_file.readline()
    while line.split(" ")[0] == oldquery:
        line = qrel_file.readline()

qrel_file.close()
topic_file.close()
