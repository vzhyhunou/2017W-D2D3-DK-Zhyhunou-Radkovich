import http.client

conn = http.client.HTTPConnection("localhost:8080")

payload = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n                  xmlns:gs=\"http://epam.by\">\n    <soapenv:Header/>\n    <soapenv:Body>\n        <gs:getUserEmailRequest>\n            <gs:name>BBB</gs:name>\n        </gs:getUserEmailRequest>\n    </soapenv:Body>\n</soapenv:Envelope>"

headers = {
    'content-type': "text/xml",
    'cache-control': "no-cache"
    }

conn.request("POST", "/ws", payload, headers)

res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))