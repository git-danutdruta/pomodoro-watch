[![<Rainy>](https://img.shields.io/circleci/build/gh/git-danutdruta/pomodoro-watch?style=plastic&token=f34f1aaa34bdd4be3a71c2c75cf4e9e924f76f6b)]()

# Pomodoro-watch
Java Swing app useful for time tracking in work/pause sessions. 

Pomodoro  is a time management, break down work into intervals, traditionally 25 minutes in length, separated by short breaks of 5 minutes.
 Developed in manner of loose coupling using MVC pattern.

![alt text](https://github.com/git-danutdruta/pomodoro-watch/blob/master/static/pomodoro_screenshot.png) &nbsp;&nbsp;&nbsp;
![alt text](https://github.com/git-danutdruta/pomodoro-watch/blob/master/static/pomodoro_pause_time.png)
# UI
The application shows the following features:
> - settings
>  
>   ![alt text](https://github.com/git-danutdruta/pomodoro-watch/blob/master/static/pomodoro_settings_dialog.png)
>   - duration of work session
>   - duration of pause/big pause session
>   - number of cycles after you can take a big break 
> - play button
> - pause button
> - reset button
> - sound button
> - elapsed work sessions

In settings section you can modify work time session, break/big brake duration and after how many working sets
you can take a big pause.
The data it is saved in ***conf*** folder, into ***config.json***.

# Flow
If there's no configuration file, default one will be created with following values : 
> - 25 min - work session
> - 5 min - pause
> - 15 min - big break
> - 4 - number of work sessions after you can get a big brake.
> - static/sound-on.mp3 - test sound

The cycles counter will increase only if a working session is completed.
The color of label, that show countdown timer, alternate depending on type of session (e.g. <span style="color:green">green</span> on ***break*** & <span style="color:blue">blue</span> on ___TheZone___).    