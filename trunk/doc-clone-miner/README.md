# English #

Document Clone Miner

## Requirements ##

### Python ###

* Python 3.4+
* PyQt5
* PyContracts
* pygments
* NumPy

Not exact versions, but preferrably most recent ones

### System ###

* `x86` or `x86_64` architecture PC to run Clone Miner, Windows or UN*X.
* on UN*Xes - [https://www.winehq.org/](Wine) to run CloneMiner

## License ##
Sources with PyQt mentioned are licensed under GPL v3.
Everything other is licensed under LGPL v3.

# Русский язык #

Document Clone Miner -- инструмент поиска клонов и рефакторинга документации в рамках проекта DocLine.

## Общая информация ##

Document Clone Miner -- легковесный инструмент, который можно запускать (стартовый файл -- `element_miner_ui.py`)
прямо из рабочего каталога репозитория. Системные требования инструмента достаточно простые, см. раздел "Требования" ниже.

После запуска будет показано окно настройки поиска клонов в документации. 

Пользователь может выбрать:

* минимальную длину искомых клонов
* способ проверки и восстановления нарушений XML-разметки
* дополнительные опции поиска клонов:
    * разрешать/запрещать пересечения клонов
    * ограничивать (и чем) дисперсию размера вариативного фрагмента при поиске клонов с точкой расширения
    * отфильтровывать бессмысленные клоны, состоящие из языковых оборотов
*  исходный DRL-файл для анализа

После нажатия кнопки "OK" инструмент какое-то время будет работать, после чего предложит список
кандидатов на создание информационных элементов и элементов словаря DRL. При запуске из DocLine (Eclipse) результаты рефакторинга будут сохранены в исходном файле, для которого рефакторинг был запущен. При отдельном запуске -- в файл с именем `<исходный файл>.reformatterd/refactored`.

## Требования ##

### Python ###

* Python 3.4+
* PyQt5
* PyContracts
* pygments
* NumPy

Not exact versions, but preferrably most recent ones

### Системные ###

* `x86` or `x86_64` architecture PC to run Clone Miner, Windows or UN*X.
* для UN*X-подобных систем -- [https://www.winehq.org/](Wine) для запуска CloneMiner

## Лицензия ##

Исходные тексты, в которых используется PyQT, лицензированы под GPL v3.
Остальные исходные тексты лицензированы под LGPL v3.