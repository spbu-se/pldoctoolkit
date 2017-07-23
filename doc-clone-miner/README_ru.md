Documentation Refactoring Toolkit -- инструмент поиска клонов и рефакторинга
документации в рамках проекта DocLine.

Общая информация
================

Documentation Refactoring Toolkit -- легковесный инструмент, который можно
запускать (стартовый файл -- `element_miner_ui.py`) прямо из рабочего каталога
репозитория без полноценного развёртывания DocLine. Системные требования
инструмента достаточно простые, см. раздел "Требования" ниже.

После запуска будет показано окно настройки поиска клонов в документации. 

Пользователь может выбрать:

* минимальную длину искомых клонов
* способ проверки и восстановления нарушений XML-разметки
* дополнительные опции поиска клонов:
    * разрешать/запрещать пересечения клонов
    * ограничивать (и чем) дисперсию размера вариативного фрагмента при
      поиске клонов с точкой расширения
    * отфильтровывать бессмысленные клоны, состоящие из языковых оборотов
*  исходный DRL-файл для анализа

После нажатия кнопки "OK" инструмент какое-то время будет работать, после чего
предложит список кандидатов на создание информационных элементов и элементов
словаря DRL. При запуске из DocLine (Eclipse) результаты рефакторинга
будут сохранены в исходном файле, для которого рефакторинг был запущен.
При отдельном запуске -- в файл с именем
`<исходный файл>.reformatterd.refactored`.

Примеры / Тесты
---------------

В каталоге `tests/documentation` содержится документация ряда проектов с
открытым исходным кодом. Для удобства анализа документация каждого проекта
собрана в один XML-файл, по одному файлу на проект.

Требования
==========

Python
------

* 64 bit [Python 3.6.x](https://www.python.org/downloads/)
* [Python-Levenshtein](http://www.lfd.uci.edu/~gohlke/pythonlibs/#python-levenshtein) и [CRlibm](https://pypi.python.org/pypi/crlibm):
  * Для Windows:
    * Обновите Pip: `python -m pip install --upgrade pip`
    * Загрузите архивы WHL: [this](python_whl/python_Levenshtein-0.12.0-cp36-cp36m-win_amd64.whl) and [this](python_whl/crlibm-1.0.3-cp36-cp36m-win_amd64.whl)
    * `pip install python_Levenshtein-0.12.0-cp36-cp36m-win_amd64.whl crlibm-1.0.3-cp36-cp36m-win_amd64.whl`
  * Для Linux, если установлен GCC, проще эти два пакета собрать самостоятельно:
    * `pip install python_Levenshtein crlibm`
* PyQt, QuaMash, LXML, PyContracts, pygments, NumPy, intervaltree, bottle, pyinterval, pypandoc — `pip install pyqt5 quamash lxml PyContracts pygments NumPy intervaltree bottle pyinterval pypandoc`


Системные
---------

* Компьютер архитектуры `x86_64` для запуска Clone Miner, Windows или UN*X
* [Pandoc](http://pandoc.org/)
* для Windows:
    * [.NET Framework 4.5](https://www.microsoft.com/ru-ru/download/details.aspx?id=30653) для запуска Fuzzy Finder
* для UN*X-подобных систем:
    * [Wine](https://www.winehq.org/) для запуска Clone Miner
    * [Mono](http://www.mono-project.com/) 4.4.0+ для запуска Fuzzy Finder


Лицензии
========

Исходные тексты, в которых используется PyQT, лицензированы под GPL v3.
Документация в тестах лицензирована в соответствии с лицензиями её исходных материалов.
Остальные тексты и исходные коды лицензированы под LGPL v3.

Portable-дистрибутив
====================

При помощи `build_dist/dcm_pack_windows.sh` можно создать самораспаковывающийся архив.
Сценарий предназначен для работы в Windows, настройки находятся прямо в нём.
Для получения Portable-дистрибутива обращайтесь к нам.
