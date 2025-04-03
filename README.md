# ShoppingList
cd c:/Projects/ShoppingList
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

inflate и findViewById очень ресурснозатратные методы
- Отсюда долгий процесс отображения
#4.3 Adapter Зачем нужен ViewHolder

Проблемы при использовании LinerLayout
1) Метод inflate медленный и вызывается для каждого элемента
2) findViewById тоже медленный и вызывается для каждого элементанесколько раз
3) даже если изменится один элемент нужно перерисовать весь список

Что делают для recyclerView - создаются только видимые view + несколько для top и bottom , потом они переиспользуются
Адаптер решает как создать одну вью и как заполнить её данные


class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
 
     val list = listOf<ShopItem>()
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enable,parent,false)
         return ShopItemViewHolder(view)
     }
 
     override fun getItemCount(): Int  = list.size
 
     override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
         val shopItem = list[position]
         holder.tvName.text = shopItem.name
         holder.tvCount.text = shopItem.count.toString()
         holder.view.setOnClickListener({
             true
         })
     }
 
     class ShopItemViewHolder(val view:View) : RecyclerView.ViewHolder(view){
         val tvName = view.findViewById<TextView>(R.id.tv_name)
         val tvCount = view.findViewById<TextView>(R.id.tv_count)
     }
 }
 
ViewHolder при создании использует inflat и при создании ViewHolder сразу создаются ссылки на нужные view через findViewById
А в onBindViewHolder просто прописываются значения

#4.4 Баги при использовании RecyclerView
//Баг - установка цвета текста только для enabled

Решение
1) Устанавливать цвет для всех типов вью
2)Переопределить метод onViewRecycled
//вызывается когда holder планируется переиспользовать //здесь можно установить значения по default
override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
    }
#4.5 ViewType и RecycledViewPool
 getItemViewType нужен в том случае, когда нужны разные лэйауты для разных элементов
 
 class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
 
     companion object {
         const val VIEW_TYPE_ACTIVE = 1
         const val VIEW_TYPE_INACTIVE = 0
         const val MAX_POOL_SIZE = 5
     }
 
     var count = 0
     var shopList = listOf<ShopItem>()
         set(value) {
             field = value
             notifyDataSetChanged()
         }
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        
         Log.d("Adapter", "onCreateViewHolder count = ${++count}")
         val view =
             LayoutInflater.from(parent.context).inflate(
                 if (viewType == VIEW_TYPE_ACTIVE) R.layout.item_shop_enable else R.layout.item_shop_disabled,
                 parent,
                 false
             )
         return ShopItemViewHolder(view)
     }

     override fun getItemCount(): Int = shopList.size
 
     override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
         val shopItem = list[position]
         val shopItem = shopList[position]
 
         holder.tvName.text = shopItem.name
         holder.tvCount.text = shopItem.count.toString()
 
         holder.view.setOnClickListener({
             true
         })
     }
 
     //when reused viewHolder
     override fun onViewRecycled(holder: ShopItemViewHolder) {
         super.onViewRecycled(holder)
     }
 
     override fun getItemViewType(position: Int): Int {
         val shopItem = shopList[position]
         return if (shopItem.enabled) VIEW_TYPE_ACTIVE else VIEW_TYPE_INACTIVE
     }
 
     class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
         val tvName = view.findViewById<TextView>(R.id.tv_name)
         val tvCount = view.findViewById<TextView>(R.id.tv_count)
 
     }
 }
 Есть пул вьюхолдеров
 Можно задать размер пула вьюхолдеров для каждого типа.
 fun setupRecyclerView(){
         val rwShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
         adapter = ShopListAdapter()
         rwShopList.adapter = adapter
         rwShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ACTIVE,ShopListAdapter.MAX_POOL_SIZE)
         rwShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_INACTIVE,ShopListAdapter.MAX_POOL_SIZE)
 
     }
	 
#4.6 Добавление слушателей

    fun setupRecyclerView(){
         val rwShopList = findViewById<RecyclerView>(R.id.rv_shop_list)         
         with(rwShopList) {
             shopListAdapter = ShopListAdapter()
             adapter = shopListAdapter
             recycledViewPool.setMaxRecycledViews(
                 ShopListAdapter.VIEW_TYPE_ACTIVE,
                 ShopListAdapter.MAX_POOL_SIZE
             )
             recycledViewPool.setMaxRecycledViews(
                 ShopListAdapter.VIEW_TYPE_INACTIVE,
                 ShopListAdapter.MAX_POOL_SIZE
             )
         }
 
         setupLongClickListener()
         setupClickListener()
         setupSwipeListener(rwShopList)
 
     }
 
     private fun setupSwipeListener(rwShopList: RecyclerView) {
         val callback = object : ItemTouchHelper.SimpleCallback(
             0,
             ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
         ) {
             override fun onMove(
                 recyclerView: RecyclerView,
                 viewHolder: RecyclerView.ViewHolder,
                 target: RecyclerView.ViewHolder
             ): Boolean {
                 return false
             }
 
             override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                 val item = shopListAdapter.shopList[viewHolder.adapterPosition]
                 viewModel.deleteShopItem(item)
             }
 
         }
         val itemTouchHelper = ItemTouchHelper(callback)
         itemTouchHelper.attachToRecyclerView(rwShopList)
     }
 
     private fun setupClickListener() {
         shopListAdapter.onShopItemClickListener = {
             Log.d(TAG, "onClickListener $it")
         }
     }
 
     private fun setupLongClickListener() {
         shopListAdapter.onShopItemLongClickListener = {
             viewModel.changeEnableState(it)
         }
     }
	 
	 class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
 
 в Java
	//functional interface - interface with one method --> lambda
     interface OnShopItemLongClickListener{
         fun onShopItemLongClick(shopItem: ShopItem)
     }
		*************
 в  Kotlin
     var onShopItemLongClickListener : ((ShopItem) -> Unit)? = null
     var onShopItemClickListener : ((ShopItem) -> Unit)? = null
		**************
 
     override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
         val shopItem = shopList[position]
 
         holder.tvName.text = shopItem.name
         holder.tvCount.text = shopItem.count.toString()
 
          holder.view.setOnLongClickListener() {
             onShopItemLongClickListener?.invoke(shopItem)
             true        
         }
         holder.view.setOnClickListener(){
             onShopItemClickListener?.invoke(shopItem)
         }
     }
 
 
     
    
	 
#4.7 Проблемы при использовании notifyDatasetChanged
При изменении одного элемента  полностью перезаполняется видимый список и для каждого элемента вызываетмя OnBindViewHolder
notifyDatasetChanged - не показывает, какой элемент изменился
Нужно использовать более конкретные методы изменения списка - все они говорят,что конкретно произошло
notifyItemChanged(int), 
notifyItemInserted(int), 
notifyItemRemoved(int), 
notifyItemRangeChanged(int, int), 
notifyItemRangeInserted(int, int), 
notifyItemRangeRemoved(int, int) 
Это позволяет перерисовать только нужные элементы + добавить анимцию без лишних усилий
#4.8 Реализация через DiffUtil и простой адаптер
Есть старый список, передам новый и адаптер сам определяет, что изменилось и вызывает соотвествующий методы
Есть два способа, первый работает более медленно, второй способо проще и быстрее

ПЕРВЫЙ
Создаём колбэк - сравнивает списки

class ShopListDiffCallback(
     private val oldList : List<ShopItem>,
     private val newList : List<ShopItem>
 ) : DiffUtil.Callback(){
     override fun getOldListSize(): Int  = oldList.size
 
     override fun getNewListSize(): Int  = newList.size
 
     override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
         val oldItem : ShopItem = oldList[oldItemPosition]
         val newItem : ShopItem = newList[newItemPosition]
         return oldItem.id == newItem.id
     }
 
     override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
         val oldItem : ShopItem = oldList[oldItemPosition]
         val newItem : ShopItem = newList[newItemPosition]
         return oldItem == newItem
     }
 
 }
 
 И переписываем передачу списка
 var shopList = listOf<ShopItem>()
         set(value) { 
             //notifyDataSetChanged()
             val callback = ShopListDiffCallback(shopList,value)
             val diffResult = DiffUtil.calculateDiff(callback) - здесь считаются все изменения для адаптера РАБОТАЕТ В ГЛАВНОМ ПОТОКЕ!!!
             diffResult.dispatchUpdatesTo(this) - применить изменения
             field = value - сохранить новый списко
         }

ВТОРОЙ:
#4.9 Реализация через ListAdapter - diffUtill работает в фоне

Создаём колбэк - сравнивает только элементы

class ShopItemDiffCallback : DiffUtil.ItemCallback<ShopItem>() {
     override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
         return oldItem.id == newItem.id
     }
 
     override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
         return oldItem == newItem
     }
 }
 
 Далее меняем адаптер
 Наследуемся от ListAdapter - его преимущесто - вся работа со списком скрыта внутри ListAdapter
 //class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
 class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_ACTIVE = 1
        const val VIEW_TYPE_INACTIVE = 0
        const val MAX_POOL_SIZE = 5
    }

    var count = 0
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(
                if (viewType == VIEW_TYPE_ACTIVE) R.layout.item_shop_enable else R.layout.item_shop_disabled,
                parent,
                false
            )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder count = ${++count}")
        val shopItem = getItem(position)

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

        holder.view.setOnLongClickListener() {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.view.setOnClickListener() {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) VIEW_TYPE_ACTIVE else VIEW_TYPE_INACTIVE
    }

Передача списка
viewModel.shopList.observe(this){
            //Log.d(TAG, "Observe = $it")
            // shopListAdapter.shopList = it
             shopListAdapter.submitList(it)
         }
}
#4.10 Вопросы


#5 Работа над вторым экраном
#5.1 Макеты для ShopItemActivity
#5.2 Создание ShopItemViewModel

class ShopItemViewModel : ViewModel() {
     private val repository = ShopListRepositoryImpl()
     private val getShopItemUseCase = GetShopItemUseCase(repository)
     private val addShopItemUseCase = AddShopItemUseCase(repository)
     private val editShopItemRepository = EditShopItemUseCase(repository)
 
     fun getShopItem(id : Int){
         val item = getShopItemUseCase.getShopItem(id)
     }
 
     fun addShopItem(inputName : String?, inputCount : String?){
         val name = parseName(inputName)
         val count = parseCount(inputCount)
         val fieldsValid = validateInput(name,count)
         if(fieldsValid) {
             addShopItemUseCase.addShopItem(ShopItem(name,count,true))
         }
     }
 
     fun editShopItem(shopItem: ShopItem,inputName : String?, inputCount : String?){
         val name = parseName(inputName)
         val count = parseCount(inputCount)
         val fieldsValid = validateInput(name,count)
         if(fieldsValid) {
             editShopItemRepository.editShopItem(ShopItem(name,count,true,shopItem.id))
         }
     }
 
     private fun parseName(name : String?) : String{
         return name?.trim() ?: ""
     }
 
     private fun parseCount(name : String?) : Int{
         return try {
             name?.toInt() ?: 0
         }catch (ex : Exception){
             0
         }
     }
 
     private fun validateInput(name : String, count : Int) : Boolean{
         var result = true;
         if(name.isBlank()){
             //ToDo: show error input name
             result = false
         }
         if(count <= 0){
             //ToDo: show error input count
             result = false
         }
         return result
     }
 }
#5.3 Завершение ShopItemViewModel
#5.4 Фабричные методы newIntent

  class ShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel: ShopItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        val mode = intent.getStringExtra("extra_mode")
    }

    companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        fun newIntentAddItem(context: Context) : Intent{
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE,MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId : Int) : Intent{
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID,shopItemId)
            return intent
        }
    }
}
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

#11.20 Dependency Injection. Shopping List

#13.1 Создание провайдера

Провайдер - это интерфейс для работы с данными. 
Обычно используется если данные нужно шарить между различными приложениями

class ShopListProvider : ContentProvider() {

    fun log(text : String){
        Log.d("ShopListProvider", text)
    }
    override fun onCreate(): Boolean {
	 //true - при успешном создании провайдера
        return true
    }
	
	ContentProvider как компонент должен быть зарег. в манифесте
	
	<provider
            android:authorities="com.example.shoppinglist" - что-то вроде базового URL , обычно совпадает с названием пакета, но не обязательно, должен быть уникальным
            android:name=".data.ShopListProvider"
            android:exported="true" />

Чтобы отправлять запросы в ContentProvider необходимо получить ContentResolver

contentResolver.query(
            Uri.parse("content://com.example.shoppinglist/shop_items"),
            null,
            null,
            null,
            null
        )
		
#13.2 UriMatcher
 13.2 UriMatcher - позволяет обрабатывать запросы, которые приходят в данный провайдер
 
class ShopListProvider : ContentProvider() {

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
	//добавляем запросы , которые будем обрабатывать
        addURI(authorities, "shop_items", GET_SHOP_ITEM_QUERY)
    }
......

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        val code = uriMatcher.match(uri)
        when(code){
            GET_SHOP_ITEM_QUERY -> {

            }
        }
        log("query $uri code = $code" )
        return null
    }

    companion object{
        const val authorities = "com.example.shoppinglist"
        private const val GET_SHOP_ITEM_QUERY = 100
    }
	
	Если нужен не весь список shop_items, а один, запрос будет выглядеть так
	
	
	Uri.parse("content://com.example.shoppinglist/shop_items/3") - в этом случае в матчер добавляем addURI(authorities, "shop_items/#", GET_SHOP_ITEM_BY_ID)
	или так
	Uri.parse("content://com.example.shoppinglist/shop_items/Jonh") в этом addURI(authorities, "shop_items/*", GET_SHOP_ITEM_BY_NAME)
	
	
	 private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(authorities, "shop_items", GET_SHOP_ITEM_QUERY)
        addURI(authorities, "shop_items/#", GET_SHOP_ITEM_BY_ID)
        addURI(authorities, "shop_items/*", GET_SHOP_ITEM_BY_NAME)
    }
}
	
#13.3 Загрузка данных из бд. Cursor

Cursor - интерфейс, который позволяет читать данные из бд, что-то вроде итератора
Курсор изначально указывает на позицию -1 

Context в провайдере и есть Application

Room поддерживает доступ к БД через Cursor

в курсоре нет boolean используем int > 0 == true
Значение получаем по индексу колонки, индекс берём по названию колонки.
Cursor итератор на БД и он хранит ссылку на БД, поэтому после использования его нужно освободить - вызвать метод close()
 
 override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

       val code = uriMatcher.match(uri)
       log("query $uri code = $code" )
       return when(code){
            GET_SHOP_ITEM_QUERY -> {
                 shopListDao.getShopListCursor()
            }
           else -> {
               null
           }
        }
    }
	
	
	thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.shoppinglist/shop_items"),
                null,
                null,
                null,
                null
            )
            log("cursor = $cursor")
            while(cursor?.moveToNext() == true){
                log("${cursor.columnCount}")

                for(names in cursor.columnNames){
                    log("columnNames = ${names}")
                }

                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0

                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )

                log("shopItem = $shopItem")
            }
            
            cursor?.close()
        }


# 13.4 Вставка данных. Класс ContentValues

ContentValues - значения, которые хранятся парой

insert возвращает URI - взтавляет запись и возвращает адрес этой записи, мы не используем, поэтому возвращаем null

override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val code = uriMatcher.match(uri)
        log("query $uri code = $code" )
        when(code) {
            GET_SHOP_ITEM_QUERY -> {
                if(values == null){
                    return null
                }

                val id = values.getAsInteger(KEY_ID)
                val name = values.getAsString(KEY_NAME)
                val count = values.getAsInteger(KEY_COUNT)
                val enabled = values.getAsBoolean(KEY_ENABLED)

                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )

                shopListDao.addShopItemSync(mapper.mapEntityToDbModel(shopItem))

            }
        }
        return null
    }
	
	
	private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            val id = 0
            val name = binding.etName.text.toString()
            val count = binding.etCount.text.toString()
            val enabled = true
            //viewModel.addShopItem(binding.etName.text.toString(), binding.etCount.text.toString())
            thread {
                context?.contentResolver?.insert(Uri.parse("content://com.example.shoppinglist/shop_items"),
                    ContentValues().apply {
                        put(ShopListProvider.KEY_ID, id)
                        put(ShopListProvider.KEY_NAME, name)
                        put(ShopListProvider.KEY_COUNT, count.toInt())
                        put(ShopListProvider.KEY_ENABLED, enabled)
                    }
                )
            }
        }
    }

#13.5 Удаление данных. Selection и Selection Args

override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int

"DELETE FROM shop_items WHERE id=:shopItemId AND nsme = :name"

когда вызываем метод удаления, то пишется такая строчка "DELETE FROM shop_items WHERE",
остальное в selection в виде "id= ? AND nsme = ?" вместо значений вопросы
и этими значениями является массив строк selectionArgs

Это для общего развития
Т.к. мы используем ROOM то selection игнорируем, а в selectionArgs будем передавать id объекта

Метод delete возвращает Int - означает, сколько строк в БД было удалено


  override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val code = uriMatcher.match(uri)
        log("query $uri code = $code" )
        when(code) {
            GET_SHOP_ITEM_QUERY -> {
                //"DELETE FROM shop_items WHERE id=:shopItemId AND nsme = :name"
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopListDao.deleteShopItemSync(id)

            }
        }
        return 0
    }
	
	
	thread {
                contentResolver.delete(
                        Uri.parse("content://com.example.shoppinglist/shop_items"),
                        "",
                        arrayOf(item.id.toString())
                    )
            }
			
#13.6 Получение данных в другом приложении

Для использования этого провайдера в другом приложении нужно прописать в манифесте (начиная с 11 версии андроида)

<queries>
    <provider android:authorities="com.example.shoppinglist"/>
</queries>

В остальном всё тоже самое