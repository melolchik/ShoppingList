# ShoppingList

Clean Architecture - убирает связанность кода
3 слоя!
# Data - Работа с данными, загрузка, сохранение
# Domain - Слой бизнес логики, ни от чего не зависит. С него начинаем
# Presentation - вывод данных, зависит от ОС

#3.3 Domain. Часть 1
SOLID
S - Single responsibility - Принцип единой ответственности
Классы - interactor or useCase

# 3.4. Domain. Часть 2
Репозиторий

# 3.5. Data- слой
Реализация репозитория

#3.6 Presentation

Presentation знает всё о domain
Data слой тоже всё знает о domain
Presentation и Data-слой не должны знать друг о друге ничего
Взаимодействие Activity и ViewModel должно происходить через LiveData
Используем MutableLiveData setValue putValue

#3.7 Presentation Part2 Автообновление списка

#4 RecyclerView

#4.1 Макеты 
#4.2 Создание через LinerLayout
#4.3 Adapter Зачем нужен ViewHolder
ViewHolder
- inflat
- findViewById
#4.4 Баги при использовании RecyclerView
#4.5 ViewType и RecycledViewPool
#4.6 Добавление слушателей

