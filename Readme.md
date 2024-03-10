Программа, симулирующая работу системы контроля заказов в ресторане.

Структура
-

В программе присутствуют следующие классы:
* Dish 
* Menu
* User (запечатанный класс)
* Visitor (наследник User)
* Admin (наследник User)
* System
* Controller

Соответствие критериям оценки:
-
1. Программа написана в соответствии с принципами ООП и SOLID
2. Программа реализует аутентификацию пользователей, хранит известные имена
пользователей в текстовом файле
3. Программа использует следующие шаблон проектирования: "Наблюдатель"
4. Хранение логинов пользователей осуществляется в отдельном текстовом файле.
Также сериализуется класс System для сохранения данных между запусками программы
5. Для обработки заказов реализована многопоточность путем использования корутин
6. Присутствует подробное описание правил пользования программой и выбора
использованного шаблона проектирования (см. ниже)
7. Реализован понятный интерфейс на английском языке


Шаблон "Наблюдатель"
-
Данный шаблон используется в двух ситуациях:
1. Информирование посетителя о готовности заказа
2. Предоставление информации администратору о значении общей прибыли (по запросу)


Правила пользования приложением:
-
Данное приложение является консольным, соотвественно должно запускаться через
консоль. Взиаможействие с приложением происходит также в окне консоли: программа
выводит различные текстовые сообщения и принимает текстовые сообщения пользователя.
Программа информирует пользователя о тех действиях, которые он можета сделать 
в каждый момент времени. Программа реализет естественное прекращение выполнения
(путем взаимодействия с интерфейсом, без принужитльного закрытия окна консоли).
Программ сохраняет все необходимые данные (смю выше) перед прекращением работы
и использует их при последующих запусках). При первом запуске программа не содержит
никаких предзагруженных данных, все они вносятся во время непосредственной работы
программы (список логинов, меню)