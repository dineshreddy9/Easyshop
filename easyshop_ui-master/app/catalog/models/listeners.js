var Events = require('../../context/events').events;
var listeners = function(eventBus, model) {

	eventBus.on(Events.catalog.getAllItems, (evt) => {
		model.getAllItems();
	});

	eventBus.on(Events.catalog.currentItem, (evt, value) => {
		model.setCurrentItem(value);
	});

	eventBus.on(Events.catalog.autoSave, (evt, id, value) => {
		model.autoSave(id, value);
	});

	eventBus.on(Events.catalog.updateItem, (evt) => {
		model.updateItem();
	});

	eventBus.on(Events.catalog.createItem, (evt) => {
		model.createItem();
	});

	eventBus.on(Events.catalog.deleteItem, (evt, id) => {
		model.deleteItem(id);
	});

	eventBus.on(Events.catalog.itemDetails, (evt, id, purpose) => {
		model.itemDetails(id, purpose);
	});

	eventBus.on(Events.catalog.addToCart, (evt, id) => {
		model.addToCart(id);
	});
	eventBus.on(Events.messages.getMessages, () => {
		model.getMessages();
	});

	eventBus.on(Events.messages.markRead, (evt, msgId) => {
		model.markRead(msgId);
	});
};

module.exports = listeners;