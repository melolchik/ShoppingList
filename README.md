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
#6.7 Взаимодействие Activity с фрагментом

Если фрагмент что-то должен передать activity, то это делается через Interface. 
Activity должна реализовать этот Interface
Получение экземпляра слушателя делается в методе onAttach через явное приведенеи типа. 
Context = Activity
Если реализация интерфейса обязательно - необходимо бросать исключение  
Снаружи устанавливать нельзя, значения будут сброшены при пересоздании фрагмента

#6.8 Жизненный цикл фрагментов
Activity
onCreate - создана, но не видима
onStart - видима но не в фокусе
onResume - видима и в фокусе
onPause - видима, но потеряла фокус
onStop - активити невидима
onDestroy - уничтожена
Fragment - те же, кроме onRestart
дОполнительные
onAttach - прикрепление к activity
onDetach - открепление от активити
onCreateView --> onDestroyView
onViewCreated --> с этого момента можно работать с View

Переход ко второй активит с фрагментом
2024-11-07 11:25:29.109 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onPause
2024-11-07 11:25:29.143 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemActivity: onCreate
2024-11-07 11:25:29.151 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemActivity: onStart

2024-11-07 11:25:29.153 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onAttach
2024-11-07 11:25:29.154 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onCreate
2024-11-07 11:25:29.154 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onCreateView
2024-11-07 11:25:29.242 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onViewCreated
2024-11-07 11:25:29.248 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onStart

2024-11-07 11:25:29.249 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemActivity: onResume
2024-11-07 11:25:29.250 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onResume
2024-11-07 11:25:29.901 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onStop
//Возврат к первой активити
2024-11-07 11:26:03.676 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemActivity: onPause
2024-11-07 11:26:03.677 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onPause
2024-11-07 11:26:03.693 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onRestart
2024-11-07 11:26:03.693 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onStart
2024-11-07 11:26:03.694 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onResume
2024-11-07 11:26:04.258 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemActivity: onStop
2024-11-07 11:26:04.259 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onStop
2024-11-07 11:26:04.260 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemActivity: onDestroy
2024-11-07 11:26:04.263 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onDestroyView
2024-11-07 11:26:04.265 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onDestroy
2024-11-07 11:26:04.265 13096-13096 COMMON                  com.example.shoppinglist             D  ShopItemFragment: onDetach

Поворот активити
2024-11-07 11:28:39.174 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: Observe = [ShopItem(name=Name 0, count=0, enabled=false, id=0), ShopItem(name=Name 1, count=1, enabled=false, id=1), ShopItem(name=Name 2, count=2, enabled=true, id=2), ShopItem(name=Name 3, count=3, enabled=false, id=3), ShopItem(name=Name 4, count=4, enabled=false, id=4), ShopItem(name=Name 5, count=5, enabled=false, id=5), ShopItem(name=Name 6, count=6, enabled=false, id=6), ShopItem(name=Name 7, count=7, enabled=false, id=7), ShopItem(name=Name 8, count=8, enabled=true, id=8), ShopItem(name=Name 9, count=9, enabled=false, id=9)]
2024-11-07 11:28:39.174 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onResume
2024-11-07 11:28:49.611 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onPause
2024-11-07 11:28:49.612 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onStop
2024-11-07 11:28:49.614 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onDestroy
2024-11-07 11:28:49.653 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onCreate
2024-11-07 11:28:49.673 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onStart
2024-11-07 11:28:49.674 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: Observe = [ShopItem(name=Name 0, count=0, enabled=false, id=0), ShopItem(name=Name 1, count=1, enabled=false, id=1), ShopItem(name=Name 2, count=2, enabled=true, id=2), ShopItem(name=Name 3, count=3, enabled=false, id=3), ShopItem(name=Name 4, count=4, enabled=false, id=4), ShopItem(name=Name 5, count=5, enabled=false, id=5), ShopItem(name=Name 6, count=6, enabled=false, id=6), ShopItem(name=Name 7, count=7, enabled=false, id=7), ShopItem(name=Name 8, count=8, enabled=true, id=8), ShopItem(name=Name 9, count=9, enabled=false, id=9)]
2024-11-07 11:28:49.674 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onResume
2024-11-07 11:28:59.186 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onPause
2024-11-07 11:28:59.188 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onStop
2024-11-07 11:28:59.189 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onDestroy
2024-11-07 11:28:59.227 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onCreate
2024-11-07 11:28:59.252 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onStart
2024-11-07 11:28:59.253 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: Observe = [ShopItem(name=Name 0, count=0, enabled=false, id=0), ShopItem(name=Name 1, count=1, enabled=false, id=1), ShopItem(name=Name 2, count=2, enabled=true, id=2), ShopItem(name=Name 3, count=3, enabled=false, id=3), ShopItem(name=Name 4, count=4, enabled=false, id=4), ShopItem(name=Name 5, count=5, enabled=false, id=5), ShopItem(name=Name 6, count=6, enabled=false, id=6), ShopItem(name=Name 7, count=7, enabled=false, id=7), ShopItem(name=Name 8, count=8, enabled=true, id=8), ShopItem(name=Name 9, count=9, enabled=false, id=9)]
2024-11-07 11:28:59.253 13096-13096 COMMON                  com.example.shoppinglist             D  MainActivity: onResume

#6.9 Собеседование по теме Fragments
#7.18 Data Binding внутри адаптера RecyclerView
#7.19 Интеграция Data Binding в Shopping List
#8.6 Создание базы данных для приложения Shopping List
#8.7 Mappers
#8.8 MediatorLiveData и Transformations.map
#8.9 App Inspection и auto generate
#8.10 Coroutine Scope и Dispatchers
Dispatchers.Mian
Dispatchers.IO - фоновый поток,макс кол-во 64
Dispatchers.Default - для долгих выч, макс. кол-во потоков = кол-ву ядер процессора, но не меньше 2х
#8.11 ViewModelScope

