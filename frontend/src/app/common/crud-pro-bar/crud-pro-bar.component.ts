import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-crud-pro-bar',
  templateUrl: './crud-pro-bar.component.html',
  styleUrls: ['./crud-pro-bar.component.scss']
})
export class CrudProBarComponent implements OnInit {
  @Input() enableCreate!: boolean;
  @Input() enablePro!: boolean;
  @Input() enableDelete!: boolean;

  @Output() create: EventEmitter<any> = new EventEmitter();
  @Output() pro: EventEmitter<any> = new EventEmitter();
  @Output() delete: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  fireCreate() {
    this.create.emit();
  }

  firePro() {
    this.pro.emit();
  }

  fireDelete() {
    this.delete.emit();
  }
}
