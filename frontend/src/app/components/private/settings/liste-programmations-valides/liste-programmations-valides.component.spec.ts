import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeProgrammationsValidesComponent } from './liste-programmations-valides.component';

describe('ListeProgrammationsValidesComponent', () => {
  let component: ListeProgrammationsValidesComponent;
  let fixture: ComponentFixture<ListeProgrammationsValidesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeProgrammationsValidesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeProgrammationsValidesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
