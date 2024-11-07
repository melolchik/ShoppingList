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
#4.7 Проблемы при использовании notifyDatasetChanged
  полностью перезаполняется видимый список и для каждого элемента вызываетмя OnBindViewHolder
#4.8 Реализация через DiffUtil и простой адаптер
#4.9 Реализация через ListAdapter - diffUtill работает в фоне
#4.10 Вопросы


#5 Работа над вторым экраном
#5.1 Макеты для ShopItemActivity
#5.2 Создание ShopItemViewModel
#5.3 Завершение ShopItemViewModel
#5.4 Фабричные методы newIntent
#5.5 Завершение работы над ShopItemActivity
  
  
#6 Фрагменты
#6.1 Зачем нужны фрагменты
#6.2 Установка фрагмента на экране ShopItemActivity
#6.3 Параметры во фрагменте и LifecycleOwner
#6.4 Методы require и get, работа с FragmentManager
#6.5 Правильная передача параметров во фрагмент
#6.6 Работа с бэкстэком, методы add и replace
  

