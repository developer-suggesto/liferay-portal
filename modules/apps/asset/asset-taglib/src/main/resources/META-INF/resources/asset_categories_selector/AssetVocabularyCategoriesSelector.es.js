import 'clay-multi-select';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './AssetVocabularyCategoriesSelector.soy';

/**
 * Wraps Clay's existing <code>MultiSelect</code> component that offers the user
 * a categories selection input.
 */
class AssetVocabularyCategoriesSelector extends Component {

	/**
	 * @inheritDoc
	 */
	attached(...args) {
		super.attached(...args);

		this._dataSource = this._handleQuery.bind(this);
	}

	syncCategoryIds(event) {
		this.emit(
			'categoryIdsChange',
			{
				categoryIds: this.categoryIds,
				vocabularyId: this.vocabularyIds
			}
		);
	}

	/**
	 * Opens the tag selection dialog.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleButtonClicked(event) {
		AUI().use(
			'liferay-item-selector-dialog',
			function(A) {
				var uri = A.Lang.sub(
					decodeURIComponent(this.portletURL),
					{
						selectedCategories: this.selectedItems.map(
							item => item.value
						).join(),
						singleSelect: this.singleSelect,
						vocabularyIds: this.vocabularyIds.concat()
					}
				);

				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: this.eventName,
						on: {
							selectedItemChange: function(event) {
								const selectedItems = event.newVal;

								if (selectedItems) {
									this.selectedItems = Object.keys(selectedItems).map(
										itemKey => {
											return {
												label: selectedItems[itemKey].value,
												value: selectedItems[itemKey].categoryId
											};
										}
									);
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('add'),
						title: Liferay.Language.get('select-categories'),
						url: uri
					}
				);

				itemSelectorDialog.open();
			}.bind(this)
		);
	}

	_handleInputFocus(event) {
		this.emit('inputFocus', event);
	}

	/**
	 * Converts the list of selected categories into a comma-separated serialized
	 * version to be used as a fallback for old services and implementations.
	 *
	 * @private
	 * @return {string} The serialized, comma-separated version of the selected items.
	 */
	_getCategoryIds() {
		return this.selectedItems.map(selectedItem => selectedItem.value).join();
	}

	/**
	 * Shows the category error.
	 *
	 * @private
	 */
	_handleErrorAddinglabelItem(event) {
		if (event.data.itemDoesNotExists) {
			this._typedCategory = event.target.inputValue;
			this._unexistingCategoryError = true;
		}
	}

	/**
	 * Prevents auto cleaning.
	 *
	 * @private
	 */
	_handleInputOnBlur(event) {
		event.preventDefault();

		const filteredItems = event.target.filteredItems;
		const inputValue = event.target.inputValue;

		if (filteredItems && filteredItems.length > 0 && filteredItems[0].data.label === inputValue) {
			const existingCategory = this.selectedItems.find(category => category.label === inputValue);

			if (!existingCategory) {
				const item = {
					label: filteredItems[0].data.label,
					value: filteredItems[0].data.value
				};

				this.selectedItems = this.selectedItems.concat(item);
				event.target.inputValue = '';
			}
		}
		else if (inputValue) {
			this._typedCategory = inputValue;
			this._unexistingCategoryError = true;
		}
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been added.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleItemAdded(event) {
		this.selectedItems = event.data.selectedItems;
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been removed.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleItemRemoved(event) {
		this.selectedItems = event.data.selectedItems;
	}

	/**
	 * Sync the _intpuvalue and hide the category error
	 *
	 * @private
	 * @review
	 */
	_handleSyncInputValue(val) {
		this._inputValue = val.newVal;
		this._unexistingCategoryError = false;
	}

	syncSelectedItems() {
		this.categoryIds = this._getCategoryIds();
	}

	/**
	 * Responds to user input to retrieve the list of available tags from the
	 * tags search service.
	 *
	 * @param {!string} query
	 * @private
	 */
	_handleQuery(query) {
		return new Promise(
			(resolve, reject) => {
				const serviceOptions = {
					end: 20,
					groupIds: this.groupIds,
					name: `%${query}%`,
					start: 0,
					vocabularyIds: this.vocabularyIds
				};

				serviceOptions['-obc'] = null;

				Liferay.Service(
					'/assetcategory/search',
					serviceOptions,
					categories => resolve(
						categories.map(
							category => {
								return {
									label: category.titleCurrentValue,
									value: category.categoryId
								};
							}
						)
					)
				);
			}
		);
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
AssetVocabularyCategoriesSelector.STATE = {

	/**
	 * <code>MultiSelect</code> component's input value.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @private
	 * @type {?(string|undefined)}
	 */
	_inputValue: Config.string().internal(),

	/**
	 * @default false
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @private
	 * @type {?bool}
	 */
	_unexistingCategoryError: Config.bool().value(false),

	/**
	 * Flag to indicate whether input can create an item.
	 *
	 * @default false
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @type {?bool}
	 */
	allowInputCreateItem: Config.bool().value(false),

	/**
	 * A comma separated list of selected items.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @type {?string}
	 */
	categoryIds: Config.string().value(''),

	groupIds: Config.array().value([]),

	/**
	 * Event name which fires when the user selects a display page using the
	 * item selector.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @type {?string}
	 */
	eventName: Config.string(),

	/**
	 * URL of a portlet to display the tags.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @type {?string}
	 */

	portletURL: Config.string(),

	/**
	 * List of selected items.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @type {?Array}
	 */

	selectedItems: Config.array().value([]),

	singleSelect: Config.bool().value(false),

	vocabularyIds: Config.array().value([])
};

Soy.register(AssetVocabularyCategoriesSelector, templates);

export {AssetVocabularyCategoriesSelector};
export default AssetVocabularyCategoriesSelector;