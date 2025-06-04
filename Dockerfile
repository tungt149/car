<arg>/bin/sh</arg>
              <arg>-c</arg>
              <arg>exec tail -f /dev/null</arg>
            </entrypoint>
          </container> -->
         <!--End - Support Container - Leaves container running for debugging. -->

<!--          <extraDirectories>-->
<!--            <paths>-->
<!--              <path>-->
<!--                <from>target/newrelic</from>-->
<!--                <into>/usr/local/newrelic</into>-->
<!--                <includes>newrelic.jar</includes>-->
<!--              </path>-->
<!--              <path>-->
<!--                <from>target/classes</from>-->
<!--                <into>/usr/local/newrelic</into>-->
<!--                <includes>newrelic.yml</includes>-->
<!--              </path>-->
<!--            </paths>-->
<!--          </extraDirectories>-->
        </configuration>
      </plugin>
     <!-- End - jib-maven-plugin -->