# Code design thoughts


## September 30, 2021
A group that I am coaching had a question about a Spring Boot WebFlux controller that was particularly difficult to test. Upon inspection, we determined that the controller was instantiating an object and that after writing some unit tests, it became clear that the tests would like to control that instantiated object. 

This happens a lot with test-driven development. To obtain test code coverage, you need to control an object that you don't have any control over how it's instantiated currently. You can capture it via an argument captor and verify it in the test after the fact, but if you need to set up the object in such a way that it determines an alternative pathway, this can prove to be difficult. 

In this case, the responsibility of instantiating the object could be moved to a factory and the factory  dependency injected into the controller instance. Now we have an _interception point_ that the unit tests can control for instantiating the object in the configuration that is needed for covering an execution path through the code. The factory can be mocked and injected into the controller and thus the object creation strategy can then be controlled within the test suite for the controller.