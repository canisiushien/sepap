import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-crud-toolbar-ministere',
  templateUrl: './crud-toolbar-ministere.component.html',
  styleUrls: ['./crud-toolbar-ministere.component.scss']
})
export class CrudToolbarMinistereComponent implements OnInit {
  @Input() enableCreate!: boolean;
  @Input() enableEdit!: boolean;
  @Input() enableDelete!: boolean;

  @Output() create: EventEmitter<any> = new EventEmitter();
  @Output() edit: EventEmitter<any> = new EventEmitter();
  @Output() delete: EventEmitter<any> = new EventEmitter();
  constructor() { }

  ngOnInit(): void {
  }

  fireCreate() {
    this.create.emit();
  }

  fireEdit() {
    this.edit.emit();
  }

  fireDelete() {
    this.delete.emit();
  }
}
