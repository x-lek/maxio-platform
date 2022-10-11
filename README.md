## MAXIO-Platform
Enter description ... <br/>

-----
## How to set up
Main components includes Keycloak, Postgres (data for Keycloak), Elasticsearch, Kibana, MinIO and Data API

elasticsearch
- load sample web log data
- kcuser1 assume monitoring security tag (example)


maxio-api
- authenticate users
- forward access token to elasticsearch resource server

openssl req -x509 -newkey rsa:4096 -nodes -keyout key.pem -out cert.pem -sha256 -days 3650

1. Run all containers defined in docker-compose.yml
```
docker-compose up -d
```

2. Add rp client_secret of keycloak to elasticsearch
```
docker exec -it elasticsearch /bin/bash

bin/elasticsearch-keystore add xpack.security.authc.realms.oidc.oidc1.rp.client_secret
dbzl8H15BlS3Dr4Dzg162bmHCCu3s8kz

# not needed for jwt since configuration is authentication = none
bin/elasticsearch-keystore add xpack.security.authc.realms.jwt.jwt1.client_authentication.shared_secret
dbzl8H15BlS3Dr4Dzg162bmHCCu3s8kz

````

3. Login to Kibana using the default Elasticsearch users (Elastic)

4. Add license through Kibana UI

5. Create role using API
```
curl --location --request POST 'elasticsearch:9200/_security/role/role1' \
--header 'Authorization: Basic ZWxhc3RpYzpwYXNzd29yZA==' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cluster": [],
    "indices": [
        {
            "names": [
                "post*"
            ],
            "privileges": [
                "read"
            ],
            "query": "{\"bool\": {\"should\": [{ \"term\": {\"author\":\"howdeepisyourlove127\"}},  {\"term\": {\"text\": \"starbuck\"}} ] } }",
            "allow_restricted_indices": false
        }
    ],
    "applications": [
        {
            "application": "kibana-.kibana",
            "privileges": [
                "read"
            ],
            "resources": [
                "*"
            ]
        }
    ],
    "run_as": [],
    "metadata": {},
    "transient_metadata": {
        "enabled": true
    }
    
}'
```

6. Create role mapping for group = kibana
```
curl --location --request POST 'elasticsearch:9200/_security/role_mapping/oidc-kibana' \
--header 'Authorization: Basic ZWxhc3RpYzpwYXNzd29yZA==' \
--header 'Content-Type: application/json' \
--data-raw '{
  "roles": [ "role1" ],
  "enabled": true,
  "rules": {
    "field": { "groups" : "kibana" }
  }
}'
```

7. Login Kibana using kcuser1 to verify that Role Mapping is working

8. Make Elasticsearch keystore persistence to prevent running keystore command everytime containers restart
```
docker cp elasticsearch:/usr/share/elasticsearch/config ./volumes/elasticsearch-config
```
9. Uncomment volume mount in docker-compose


For minio
- add policy file for kcuser1 user readonly mode
```
mc admin policy add local kcuser1-readonly minio-policy.json
```
