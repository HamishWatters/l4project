fold0 = open("/home/hamish/Documents/IndividualProject/QE_Words/fold0.csv", "r")
fold1 = open("/home/hamish/Documents/IndividualProject/QE_Words/fold1.csv", "r")
fold2 = open("/home/hamish/Documents/IndividualProject/QE_Words/fold2.csv", "r")
fold3 = open("/home/hamish/Documents/IndividualProject/QE_Words/fold3.csv", "r")
fold4 = open("/home/hamish/Documents/IndividualProject/QE_Words/fold4.csv", "r")

words = {}
for line in fold0.readlines():
    columns = line.split(",")
    if float(columns[4]) != 0:
        rawquery = columns[1]
        query = rawquery.replace("%20", " ").replace("/"," ").replace("-", " ").replace(".", "").replace(":", "").lower()
        tokens = query.split(" ")
        for t in tokens:
            if t in words:
                words[t][0] += 1
                words[t][1] += [float(columns[4])]
            else:
                words[t] = [1,[float(columns[4])]]

for line in fold1.readlines():
    columns = line.split(",")
    if float(columns[4]) != 0:
        rawquery = columns[1]
        query = rawquery.replace("%20", " ").replace("/"," ").replace("-", " ").replace(".", "").replace(":", "").lower()
        tokens = query.split(" ")
        for t in tokens:
            if t in words:
                words[t][0] += 1
                words[t][1] += [float(columns[4])]
            else:
                words[t] = [1,[float(columns[4])]]
for line in fold2.readlines():
    columns = line.split(",")
    if float(columns[4]) != 0:
        rawquery = columns[1]
        query = rawquery.replace("%20", " ").replace("/"," ").replace("-", " ").replace(".", "").replace(":", "").lower()
        tokens = query.split(" ")
        for t in tokens:
            if t in words:
                words[t][0] += 1
                words[t][1] += [float(columns[4])]
            else:
                words[t] = [1,[float(columns[4])]]
for line in fold3.readlines():
    columns = line.split(",")
    if float(columns[4]) != 0:
        rawquery = columns[1]
        query = rawquery.replace("%20", " ").replace("/"," ").replace("-", " ").replace(".", "").replace(":", "").lower()
        tokens = query.split(" ")
        for t in tokens:
            if t in words:
                words[t][0] += 1
                words[t][1] += [float(columns[4])]
            else:
                words[t] = [1,[float(columns[4])]]
for line in fold1.readlines():
    columns = line.split(",")
    if float(columns[4]) != 0:
        rawquery = columns[1]
        query = rawquery.replace("%20", " ").replace("/"," ").replace("-", " ").replace(".", "").replace(":", "").lower()
        tokens = query.split(" ")
        for t in tokens:
            if t in words:
                words[t][0] += 1
                words[t][1] += [float(columns[4])]
            else:
                words[t] = [1, [float(columns[4])]]

stoplist = open("../stoplist.txt")
for line in stoplist.readlines():
    if line.strip() in words:
        del words[line.strip()]
        print "killed line"

output = open("output.csv", "w")
for key in words:
    sum = 0.0
    for num in words[key][1]:
        sum += num
    output.write(key + "," + str(words[key][0]) + "," + str(sum/len(words[key][1]))+'\n')

fold0.close()
fold1.close()
fold2.close()
fold3.close()
fold4.close()