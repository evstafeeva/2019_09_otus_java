
# Сравнение разных сборщиков мусора 
Домашнее задание:
>Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа
(young, old) и время которое ушло на сборки в минуту.
Добиться OutOfMemory в этом приложении через медленное подтекание по памяти
(например добавлять элементы в List и удалять только половину).
Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало
с OOM примерно через 5 минут после начала работы.
Собрать статистику (количество сборок, время на сборки) по разным GC.
!!! Сделать выводы !!!
ЭТО САМАЯ ВАЖНАЯ ЧАСТЬ РАБОТЫ:
Какой gc лучше и почему?

В данной работе будем сравнивать несколько сборщиков мусора:
- Serial GC
- Parallel GC
- CMS (Concurrent Mark Sweep) GC
- G1 (Garbage First) GC

Тестировать сборщики мусора будем на примитивной программе: 
Создадим список, в который будем добавлять четыре элемента - массива типа Integer на 500 элементов и удалять два элемента. Таким образом создавая мусор. 

VM options:
```
-Xms512m 
-Xmx512m 
-XX:+UseSerialGC || -XX:+UseParallelGC || -XX:+UseConcMarkSweepGC || -XX:+UseG1GC
```

Приступим к сбору статистики:

### Serial Garbage Collector
GC name: Copy (young generation) 
GC name: MarkSweepCompact (old generation) 
 GC name | Sum pause, ms | Max pause, ms | Count 
 ------ | ------ | ------ | ------ 
 Copy | 181 | 79 | 6 
 MarkSweepCompact | 28 719 | 318 | 38 
Приложение работало: 4 min 44 sec, после чего было выброшено исключение OutOfMemoryError.


### Parallel Garbage Collector
GC name: Scavenge (young generation) 
GC name: MarkSweep (old generation) 
 GC name | Sum pause, ms | Max pause, ms | Count 
 ------ | ------ | ------ | ------ 
 Scavenge | 152 | 61 | 4 
 MarkSweep | 1 196 | 151 | 13 
Приложение работало: 4 min 17 sec, после чего было выброшено исключение OutOfMemoryError.

### CMS (Concurrent Mark Sweep) Garbage Collector
GC name: ParNew (young generation) 
GC name: ConcurrentMarkSweep (old generation) 
 GC name | Sum pause, ms | Max pause, ms | Count 
 ------ | ------ | ------ | ------ 
 ParNew | 247 | 61 | 14 
 ConcurrentMarkSweep | 64 289 | 5 158 | 77 
Приложение работало: 4 min 41 sec, после чего было выброшено исключение OutOfMemoryError.
В данном GC ParNew делает stop the world, в то время как ConcurrentMarkSweep работает одновременно с приложением.
Но данный GC считается устаревшим. 

### G1 (Garbage First) Garbage Collector
GC name: G1 Young Generation
GC name: G1 Old Generation
 GC name | Sum pause, ms | Max pause, ms | Count 
 ------ | ------ | ------ | ------ 
 G1 Young Generatio | 400 | 26 | 42 
 G1 Old Generation | 1 599 | 162 | 11 
Приложение работало: 4 min 44 sec, после чего было выброшено исключение OutOfMemoryError.

### Выводы
1. Serial Garbage Collector работает в один поток и делает STW 28 900ms, что является максимальным из всех рассматриваемых Garbage Collector-ов. 
2. Parallel Garbage Collector - сборщик, который использует все ресурсы по максимуму, но не умеет работать одновременно с приложением, соответственно при каждой сборке делает STW, что недопустимо при борьбе за Latency. При этом сообщение о ООМ получает в течение 4 мин 41 сек, что является рекордным из всех рассматриваемых. Отсюда сделаем вывод, что данный сборщик работает быстрее всех.
3. CMS - выполняет большую часть работы одновременно с приложением, что влияет на производительность, но минимально влияет на время отклика приложения.
4. G1- новое поколение сборщиков. По большей части работает одновременно с приложением, тем самым минимизируя STW. Данный сборщик меньше всех обращается к old generation(11  из 56), что ускоряет его работу. 

Таким образом становится ясно, что для более производительной работы приложения необходимо использовать Parallel GC, но при упоре на Latency G1 является оптимальным выбором.
