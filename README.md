# reactive-lab5
Akka fault tolerance and remoting

(20 pkt) Proszę rozszerzyć system aukcyjny o mechanizm publikowania informacji o przebiegu aukcji do zewnętrznego systemu Auction Publisher przy użyciu zdalnej komunikacji za pośrednictwem nowego aktora Notifier:
Auction powiadamia lokalnego aktora Notifier o zmianie stanu aukcji poprzez komunikaty Notify zawierające informację o tytule aukcji, aktualnym kupującym i bieżącej cenie.
Notifier obsługuje komunikaty Notify i jest klientem przekazującym informacje do zewnętrznego serwera Auction Publisher.
Serwer Auction Publisher przyjmuje powiadomienia poprzez komunikaty.
(20 pkt) Proszę rozszerzyć aktora Notifier o mechanizmy fault-tolerance, tak aby działał poprawnie w wypadku awarii zewnętrznego serwera lub awarii sieci. Awarie te nie powinny zakłócać pracy systemu aukcyjnego. Wskazówka: dla każdego powiadomienia należy stworzyć nowego aktora NotifierRequest nadzorowanego przez aktora Notifier.
