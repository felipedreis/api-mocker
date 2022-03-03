# API Mocker

This project is a simple API mocker for integration tests. It's based on 
Spring Cloud, so it can take advantage of many Spring features such as Spring Config,
Actuator and so on. 

The idea behind this project is to be able to mock uat/dev/bench requests
just with configuration, so integration tests and test environments could be
more congruent with prod environments.

All mocks are configurable based on YML notation. A starting point for configuring a new mock
is adding a new provider to the config file. A provider can have many paths and a 
default behaviour (PASSTHROUGH, DENY or EMPTY). Each path has method and an answer.

```yaml
mock:
    providers:
        - provider: http://foo.bar.com
          defaultBehaviour: passthrough
          paths: 
            - path: /users/
              method: GET
              answer: 
                status: 200
                headers:
                    Content-Type: application/json
                body: |
                {
                    "foo": "bar"
                }
```

After configuring it, you can run the Application through th MockerApplication class 
on localhost:8888 and then you call on terminal:

```shell
curl -XGET http://localhost:8888/http://foo.bar.com/users/
```

This will return the configured json answer with status 200. If you curl a not configured
URL, you receive the default behaviour. In the configured case, it'll go to the foo.bar.com
service, get the answer and forward to your service. 

You can use regex on url too. For example, if you want mock all possible user ids, you may configure
something like:

```yaml
mock:
  providers:
    - provider: http://foo.bar.com
      defaultBehaviour: passthrough
      paths:
        - path: /user/[0-9]+/
          method: POST
          answer:
            status: 200
            headers:
              Content-Type: application/json
            body: |
            {
              "userName": "name"
            }
```

And any userId will receive the configured answer.

Regarding the answer field, it has to be compatible with http body response. 
It must be at least a header, a status and a body.

## Next programmed functionalities

* request placeholders
* programmable answers
* error probability