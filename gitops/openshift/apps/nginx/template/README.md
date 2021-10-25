## Templates to create/update niginx containers for reverse proxy & split traffic between old webmethods and new jag-pcss-common api

### Template for Nginx 1 - To reverse proxy and split traffic between new jag-pcss-common api & the other Nginx 2 server container.
* defaultNetworkPolicies.yaml (downloaded QuickStart.yaml from above link)


### Command to execute template
1) Login to OC using login command
2) Run below command in each env. namespace dev/test/prod/tools
   ``oc process -f nginx_jag_pcss_common.yaml --param-file=nginx_jag_pcss_common.env | oc apply -f -``

### Template for Nginx 2 - To reverse proxy the traffic from Nginx 1 to the old webmethods api.


### Command to execute template
1) Login to OC using login command
2) Run below command in each env. namespace dev/test/prod/tools
   ``oc process -f nginx_jag_pcss_common_oldwm.yaml --param-file=nginx_jag_pcss_common_oldwm.env | oc apply -f -``

