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


The implemented services example is an unbounded service.

onStartCommand() : This method is called when the service is started using startService(). It receives an Intent, flags, and a unique identifier for the start request.

Inside onStartCommand:

MediaPlayer.create(): Initializes the MediaPlayer with an audio resource from the raw directory (in this case, music.mp3).
player.start(): Starts playing the audio.

return START_STICKY

This return value indicates that if the system kills the service, it should be recreated with a null intent. It allows the service to continue running until explicitly stopped.

onDestroy Method

This method is called when the service is destroyed (e.g., when stopService() is called).
player.stop(): Stops playback of the audio.
player.release(): Releases resources associated with the MediaPlayer, preventing memory leaks.

onBind Method

This method is called if another component binds to the service (using bindService()).
Returning null indicates that this is not a bound service; it does not provide any binding functionality.

