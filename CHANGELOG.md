# 2.1.1
## Fixed
- EventProcessor: negative time is detected even in an alternative `addEvent` method

# 2.1.0

## Changed
- logs changed to logback

# 2.0.0

## Changed
- immediate events: Events with zero delay are now executed immediately. The only changed method is: 
`public void addEvent(Enum type, EventHandler recipient, String owner, Object content)` in `EventProcessor`. 
The signature of the method is the same, but the behaviour changed, the event is now executed immediately.
This can result in the different order of events! To stick with the old behavior 
(adding event to the event queue with 1ms delay) you can use method 
`public void addEvent(Enum type, EventHandler recipient, String owner, Object content, long deltaTime)` with
`deltaTime` set to 1.
- start of the semantic versioning
