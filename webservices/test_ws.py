import http.client
import sys

conn = http.client.HTTPConnection("localhost:8080")

user_name = sys.argv[1]

payload = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:gs="http://epam.by">
    <soapenv:Header/>
    <soapenv:Body>
        <gs:getUserEmailRequest>
            <gs:name>{0}</gs:name>
        </gs:getUserEmailRequest>
    </soapenv:Body>
</soapenv:Envelope>
""".format(user_name)

headers = {
    'content-type': "text/xml",
    'cache-control': "no-cache"
    }

conn.request("POST", "/ws", payload, headers)

res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))