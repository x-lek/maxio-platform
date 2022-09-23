# Initial Start
docker-compose up -d

# Add rp client_secret for keycloak
docker exec -it elasticsearch /bin/bash
echo 'y' | echo '8cTRFK6QBBeacJZLgk41MypAC8SLAlJc' | bin/elasticsearch-keystore add xpack.security.authc.realms.oidc.oidc1.rp.client_secret

# login kibana using elastic user

# add license through Kibana UI

# create role using API or kibana
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

# create role mapping
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

# login using kcuser1

# make elastic keystore persistence, else need to run add keystore command everytime restart
docker cp elasticsearch:/usr/share/elasticsearch/config ./volumes/elasticsearch-config

# uncomment volume mount in docker-compose
