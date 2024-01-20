from json import load, dump

file = open('languages.json')
data = load(file)

new = []
for k, v in data.items():
    v["code"] = k
    new.append(v)

file2 = open('languages2.json', 'w')
dump(new, file2, indent=4)
