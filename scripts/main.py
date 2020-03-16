import mysql.connector
from secret import Secret

cnx = mysql.connector.connect(user=Secret.user, password=Secret.pwd, database=Secret.db_name)
cursor = cnx.cursor()

i = 0
with open("log.txt", "r", encoding="latin-1") as infile:
    for line in infile:
        try:
            query = line.split('|')[4].lstrip()
        except Exception as e:
            continue
        if 'insert' in query:
            print(query)
            try:
                cursor.execute(query)
            except mysql.connector.IntegrityError as err:
                print("Error: {}".format(err))
                print("Skipping " + query)
                continue

            i += 1
            if i == 50:
                i = 0
                cnx.commit()

cursor.close()
cnx.close()
