var App = require('./../../context/events');
var _ = require('underscore');
var $ = require('jquery');
var Immutable  = require('immutable');

var Catalog = class {
	constructor(eventBus, localstorage){
    this.eventBus = eventBus;
    this.items = [];
    this.currentItem = [];
    this.localstorage = localstorage;
    this.itemId = 0;
    this.loading = false;
    this.messages = [];
	}

	getAllItems(){
	$.ajax({
        method: 'GET',
        url: window.baseURL+'catalog/itemDetails',
        contentType: 'application/json',
        dataType: "json"
      }).done((response)=>{
        if(response != null){
          this.items = response;
        }
      }).fail((jqXHR, textStatus, errorThrown)=>{
          console.log(errorThrown);
          this.localstorage.clear();
          //window.BUS.trigger(App.events.ui.alert,['problem in getting catalog details', 'Info']);
      }).always(()=>{
        this.loading = false;
        this.eventBus.trigger(App.events.models.changed);
      });
      this.getCartCount();
	}

  getMessages(){
    var unreadMessages =[];
    $.ajax({
        method: 'GET',
        url: window.baseURL+'catalog/getMessages',
        contentType: 'application/json',
        dataType: "json"
      }).done((response)=>{
        this.messages = response;
        unreadMessages = _.filter(this.messages, msg => {
          return !(msg.read);
        });
        this.localstorage.setItem('messageCount',unreadMessages.length);
      }).always(()=>{
        this.eventBus.trigger(App.events.models.changed);
      });
  }

  markRead(msgId){
    var that = this;
    var unreadMessages =[];
    var currentMessageIndex = _.findIndex(this.messages, m => {
      return m.messageId === msgId;
    });
    $.ajax({
      method: 'PUT',
      url: window.baseURL+'catalog/messageMarkRead?messageId='+msgId,
      contentType: 'application/json',
      dataType: "json"
    }).done((response)=>{
        that.messages[currentMessageIndex].read = true;
        unreadMessages = _.filter(this.messages, msg => {
          return !(msg.read);
        });
        this.localstorage.setItem('messageCount',unreadMessages.length);
    }).always(()=>{
      this.eventBus.trigger(App.events.models.changed);
    });
  }

  getCartCount(){
    $.ajax({
        method: 'GET',
        url: window.baseURL+'cart/getCartCount',
        contentType: 'application/json',
        dataType: "json"
      }).done((response)=>{
        if(response != null){
          this.localstorage.setItem('cartCount',response.cartCount);
        }
      }).fail((jqXHR, textStatus, errorThrown)=>{
          //window.BUS.trigger(App.events.ui.alert,['problem in getting catalog details', 'Info']);
      }).always(()=>{
        this.loading = false;
        this.eventBus.trigger(App.events.models.changed);
      });
  }

  setCurrentItem(value){
    this.currentItem = value;
    this.eventBus.trigger(App.events.models.changed);
  }

  autoSave(id, value){
    this.currentItem[id] = value;
    this.eventBus.trigger(App.events.models.changed); 
  }

  updateItem(){
    $.ajax({
      method: 'PUT',
      url: window.baseURL+'catalog/updateItem',
      contentType: 'application/json',
      data: JSON.stringify(this.currentItem),
      dataType: "json"
    }).done((response)=>{
        this.currentItem = [];
    }).fail((jqXHR, textStatus, errorThrown)=>{
        window.BUS.trigger(App.events.ui.alert,['problem in getting catalog details', 'Info']);
    }).always(()=>{
      this.loading = false;
      this.eventBus.trigger(App.events.models.changed);
    });
  }

  itemInfo(){
    return({
      itemName: this.currentItem['itemName'],
      itemDescription: this.currentItem['itemDescription'],
      itemPrice: this.currentItem['itemPrice'],
      itemQuantity: this.currentItem['itemQuantity'],
      itemImage: this.currentItem['itemImage']
    })
  }

  createItem(){
    var data = this.itemInfo();
    $.ajax({
      method: 'POST',
      url: window.baseURL+'catalog/createItem',
      contentType: 'application/json',
      data: JSON.stringify(data),
      dataType: "json"
    }).done((response)=>{
      this.items = response;
    }).fail((jqXHR, textStatus, errorThrown)=>{
        window.BUS.trigger(App.events.ui.alert,['problem in getting catalog details', 'Info']);
    }).always(()=>{
      this.loading = false;
      this.eventBus.trigger(App.events.models.changed);
    });
  }

  deleteItem(id){
    $.ajax({
      method: 'DELETE',
      url: window.baseURL+'catalog/deleteItem?itemId='+id,
      contentType: 'application/json',
      dataType: "json"
    }).done((response)=>{
      var index =  _.findIndex(this.items, (d) => d.itemId === id);
      this.items = _.without(this.items,index);
    }).fail((jqXHR, textStatus, errorThrown)=>{
        window.BUS.trigger(App.events.ui.alert,['problem in getting catalog details', 'Info']);
    }).always(()=>{
      this.loading = false;
      this.eventBus.trigger(App.events.models.changed);
    });
  }

  itemDetails(itemId, routingOpts){
    var index =  _.findIndex(this.items, (d) => d.itemId === itemId);
    this.currentItem = this.items[index];
    this.routingOpts = routingOpts;
    this.eventBus.trigger(App.events.models.changed);
    window.router.setRoute('/itemDetails');
  }

  cartItem(){
    return({
      custId: this.localstorage.getItem('custId'),
      itemId: this.itemId
    })
  }

  addToCart(itemId){
    var itemToCart = this.cartItem();
    itemToCart.itemId = itemId;
    this.loading= true;
    this.eventBus.trigger(App.events.models.changed);
    $.ajax({
      method: 'POST',
      url: window.baseURL+'cart/addToCart',
      contentType: 'application/json',
      data: JSON.stringify(itemToCart),
      dataType: "json"
    }).done((response)=>{
      this.localstorage.setItem('cartCount',response.cartCount);
    }).fail((jqXHR, textStatus, errorThrown)=>{
        window.BUS.trigger(App.events.ui.alert,['problem in getting catalog details', 'Info']);
    }).always(()=>{
      this.loading = false;
      this.eventBus.trigger(App.events.models.changed);
    });
    console.log("testing");
  }

  getState(){
    return Immutable.fromJS({
      items: this.items,
      loading: this.loading,
      currentItem: this.currentItem,
      routingOpts: this.routingOpts,
      cartCount: this.localstorage.getItem('cartCount'),
      messages: this.messages
    });
  }
};

module.exports = Catalog;