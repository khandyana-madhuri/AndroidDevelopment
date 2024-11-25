# AndroidDevelopment
Services:

- Services are used to run in background to perform long-running operations.
- A service can run continuously in the background even if the application is closed or user switches to another application.
- Application components can bind itself to service to carry out inter process communication (processes can coordinate and interact with one another using a method called IPC).

Types of Services :

1. Foreground Services :
    services that notify the user about its ongoing operations are called Foreground Services such as downloading a file, user can keep track of progress in downloading and can also pause and resume the process.
2. Background Services :
    These services do not require any user intervention such as syncing or storing data fall under this service.
3. Bound Services :
    These services provide an interface for components(such as activities or fragments) to interact with the service. They remain active as long as another component is bound to them.

LifeCycle of Services :

1. Started Service (Unbounded Service) :
- A service will initiate when an application component calls the startService() method.
- Once initiated, the service can run continuously in the background even if the component is destroyed which was responsible for start of service.
- Two options are available to stop itself by using stopSelf() method.
    -> By calling stopService() method.
    -> The service can stop itself by using stopSelf() method.

2. Bounded Service :
- A service is termed as bounded when an application component binds itself with a service by calling bindService()
- To stop the execution of this service, all the components must unbind themselves from the service using unbindService()
