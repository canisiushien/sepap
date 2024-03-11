import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudToolbarMinistereComponent } from './crud-toolbar-ministere.component';

describe('CrudToolbarMinistereComponent', () => {
  let component: CrudToolbarMinistereComponent;
  let fixture: ComponentFixture<CrudToolbarMinistereComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrudToolbarMinistereComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrudToolbarMinistereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
