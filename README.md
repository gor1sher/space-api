# Суть задачки: запустить в многопоточке клиентов и узнать какой из них самый быстрый
## В пакете сервисы создал следующие классы:
### HttpClientService
### RestTemplateService 
### WebClientService
### В выше перечисленных классах реализованы клиенты
### WorkerPoolService - в этом классе реализован пул потоков клиентов, также подсчитывается и выводится в виде лога самыц быстрый клиент 